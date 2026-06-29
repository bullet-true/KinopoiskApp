package ru.ifedorov.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ifedorov.common.AppResult
import ru.ifedorov.data.mapper.toDomainCollection
import ru.ifedorov.data.mapper.toDomainFilm
import ru.ifedorov.data.mapper.toFilmEntity
import ru.ifedorov.data.source.LocalCollectionDataSource
import ru.ifedorov.data.source.LocalFilmDataSource
import ru.ifedorov.database.model.CollectionFilmCrossRef
import ru.ifedorov.database.model.UserCollectionEntity
import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.model.UserCollection
import ru.ifedorov.domain.repository.CollectionRepository
import javax.inject.Inject

internal class CollectionRepositoryImpl @Inject constructor(
    private val localCollectionDataSource: LocalCollectionDataSource,
    private val localFilmDataSource: LocalFilmDataSource
) : CollectionRepository {

    override fun observeCollections(): Flow<List<UserCollection>> =
        localCollectionDataSource.observeCollections().map { collections ->
            collections.map { it.toDomainCollection() }
        }

    override fun observeCollectionFilms(collectionId: Long): Flow<List<Film>> =
        localCollectionDataSource.observeCollectionFilms(collectionId).map { films ->
            films.map { it.toDomainFilm() }
        }

    override suspend fun createCollection(name: String): AppResult<Long> {
        val collectionId = localCollectionDataSource.insertCollection(
            UserCollectionEntity(
                name = name,
                createdAtMillis = System.currentTimeMillis()
            )
        )
        return AppResult.Success(collectionId)
    }

    override suspend fun deleteCollection(collectionId: Long): AppResult<Unit> {
        localCollectionDataSource.deleteCollection(collectionId)
        return AppResult.Success(Unit)
    }

    override suspend fun addFilmToCollection(collectionId: Long, film: Film): AppResult<Unit> {
        localFilmDataSource.upsertFilm(film.toFilmEntity())
        localCollectionDataSource.addFilmToCollection(
            CollectionFilmCrossRef(
                collectionId = collectionId,
                filmId = film.kinopoiskId,
                addedAtMillis = System.currentTimeMillis()
            )
        )
        return AppResult.Success(Unit)
    }

    override suspend fun removeFilmFromCollection(
        collectionId: Long,
        filmId: Int
    ): AppResult<Unit> {
        localCollectionDataSource.removeFilmFromCollection(
            collectionId = collectionId,
            filmId = filmId
        )
        return AppResult.Success(Unit)
    }
}
