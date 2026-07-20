package ru.ifedorov.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ifedorov.common.AppResult
import ru.ifedorov.data.mapper.toDomainFilm
import ru.ifedorov.data.mapper.toFilmEntity
import ru.ifedorov.data.source.LocalFilmDataSource
import ru.ifedorov.data.source.LocalHistoryDataSource
import ru.ifedorov.data.util.safeDataCall
import ru.ifedorov.database.model.InterestedFilmEntity
import ru.ifedorov.database.model.UserFilmStateEntity
import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.repository.UserFilmRepository
import javax.inject.Inject

internal class UserFilmRepositoryImpl @Inject constructor(
    private val localFilmDataSource: LocalFilmDataSource,
    private val localHistoryDataSource: LocalHistoryDataSource
) : UserFilmRepository {

    override fun observeFavoriteFilms(): Flow<List<Film>> =
        localFilmDataSource.observeFavoriteFilms().map { films -> films.map { it.toDomainFilm() } }

    override fun observeWantToWatchFilms(): Flow<List<Film>> =
        localFilmDataSource.observeWantToWatchFilms()
            .map { films -> films.map { it.toDomainFilm() } }

    override fun observeWatchedFilms(): Flow<List<Film>> =
        localFilmDataSource.observeWatchedFilms().map { films -> films.map { it.toDomainFilm() } }

    override fun observeInterestedFilms(): Flow<List<Film>> =
        localHistoryDataSource.observeInterestedFilms()
            .map { films -> films.map { it.toDomainFilm() } }

    override suspend fun toggleFavorite(film: Film): AppResult<Unit> =
        updateFilmState(film) { current -> current.copy(isFavorite = !current.isFavorite) }

    override suspend fun toggleWantToWatch(film: Film): AppResult<Unit> =
        updateFilmState(film) { current -> current.copy(isWantToWatch = !current.isWantToWatch) }

    override suspend fun toggleWatched(film: Film): AppResult<Unit> =
        updateFilmState(film) { current -> current.copy(isWatched = !current.isWatched) }

    override suspend fun markInterested(film: Film): AppResult<Unit> = safeDataCall {
        localFilmDataSource.upsertFilm(film.toFilmEntity())
        localHistoryDataSource.upsertInterestedFilm(
            InterestedFilmEntity(
                filmId = film.kinopoiskId,
                viewedAtMillis = System.currentTimeMillis()
            )
        )
    }

    override suspend fun clearInterestedFilms(): AppResult<Unit> = safeDataCall {
        localHistoryDataSource.clearInterestedFilms()
    }

    private suspend fun updateFilmState(
        film: Film,
        transform: (UserFilmStateEntity) -> UserFilmStateEntity
    ): AppResult<Unit> = safeDataCall {
        localFilmDataSource.upsertFilm(film.toFilmEntity())

        val currentState = localFilmDataSource.getUserFilmState(film.kinopoiskId)
            ?: UserFilmStateEntity(
                filmId = film.kinopoiskId,
                isFavorite = false,
                isWantToWatch = false,
                isWatched = false,
                updatedAtMillis = 0L
            )

        localFilmDataSource.upsertUserFilmState(
            transform(currentState).copy(updatedAtMillis = System.currentTimeMillis())
        )
    }
}
