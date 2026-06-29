package ru.ifedorov.data.source

import kotlinx.coroutines.flow.Flow
import ru.ifedorov.database.dao.UserCollectionDao
import ru.ifedorov.database.model.CollectionFilmCrossRef
import ru.ifedorov.database.model.FilmEntity
import ru.ifedorov.database.model.UserCollectionEntity
import javax.inject.Inject

internal class LocalCollectionDataSource @Inject constructor(
    private val userCollectionDao: UserCollectionDao
) {

    suspend fun insertCollection(collection: UserCollectionEntity): Long =
        userCollectionDao.insertCollection(collection)

    suspend fun deleteCollection(collectionId: Long) {
        userCollectionDao.deleteCollection(collectionId)
    }

    fun observeCollections(): Flow<List<UserCollectionEntity>> = userCollectionDao.observeCollections()

    suspend fun addFilmToCollection(crossRef: CollectionFilmCrossRef) {
        userCollectionDao.addFilmToCollection(crossRef)
    }

    suspend fun removeFilmFromCollection(collectionId: Long, filmId: Int) {
        userCollectionDao.removeFilmFromCollection(collectionId = collectionId, filmId = filmId)
    }

    fun observeCollectionFilms(collectionId: Long): Flow<List<FilmEntity>> =
        userCollectionDao.observeCollectionFilms(collectionId)
}
