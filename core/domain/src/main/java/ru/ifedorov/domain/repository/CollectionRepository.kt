package ru.ifedorov.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ifedorov.common.AppResult
import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.model.UserCollection

interface CollectionRepository {

    fun observeCollections(): Flow<List<UserCollection>>

    fun observeCollectionFilms(collectionId: Long): Flow<List<Film>>

    suspend fun createCollection(name: String): AppResult<Long>

    suspend fun deleteCollection(collectionId: Long): AppResult<Unit>

    suspend fun addFilmToCollection(collectionId: Long, film: Film): AppResult<Unit>

    suspend fun removeFilmFromCollection(collectionId: Long, filmId: Int): AppResult<Unit>
}
