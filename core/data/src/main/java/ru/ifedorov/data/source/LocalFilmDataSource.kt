package ru.ifedorov.data.source

import kotlinx.coroutines.flow.Flow
import ru.ifedorov.database.dao.FilmDao
import ru.ifedorov.database.dao.UserFilmStateDao
import ru.ifedorov.database.model.FilmEntity
import ru.ifedorov.database.model.UserFilmStateEntity
import javax.inject.Inject

internal class LocalFilmDataSource @Inject constructor(
    private val filmDao: FilmDao,
    private val userFilmStateDao: UserFilmStateDao
) {

    suspend fun upsertFilm(film: FilmEntity) {
        filmDao.upsertFilm(film)
    }

    suspend fun upsertFilms(films: List<FilmEntity>) {
        filmDao.upsertFilms(films)
    }

    fun observeFilm(filmId: Int): Flow<FilmEntity?> = filmDao.observeFilm(filmId)

    suspend fun upsertUserFilmState(state: UserFilmStateEntity) {
        userFilmStateDao.upsertUserFilmState(state)
    }

    fun observeUserFilmState(filmId: Int): Flow<UserFilmStateEntity?> =
        userFilmStateDao.observeUserFilmState(filmId)

    fun observeFavoriteFilms(): Flow<List<FilmEntity>> = userFilmStateDao.observeFavoriteFilms()

    fun observeWantToWatchFilms(): Flow<List<FilmEntity>> = userFilmStateDao.observeWantToWatchFilms()

    fun observeWatchedFilms(): Flow<List<FilmEntity>> = userFilmStateDao.observeWatchedFilms()
}
