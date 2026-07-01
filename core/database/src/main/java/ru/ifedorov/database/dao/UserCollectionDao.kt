package ru.ifedorov.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.ifedorov.database.model.CollectionFilmCrossRef
import ru.ifedorov.database.model.FilmEntity
import ru.ifedorov.database.model.UserCollectionEntity

@Dao
interface UserCollectionDao {

    @Insert
    suspend fun insertCollection(collection: UserCollectionEntity): Long

    @Query("DELETE FROM user_collections WHERE id = :collectionId")
    suspend fun deleteCollection(collectionId: Long)

    @Query("SELECT * FROM user_collections ORDER BY createdAtMillis DESC")
    fun observeCollections(): Flow<List<UserCollectionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilmToCollection(crossRef: CollectionFilmCrossRef)

    @Query("DELETE FROM collection_films WHERE collectionId = :collectionId AND filmId = :filmId")
    suspend fun removeFilmFromCollection(collectionId: Long, filmId: Int)

    @Query(
        """
        SELECT films.* FROM films
        INNER JOIN collection_films ON films.kinopoiskId = collection_films.filmId
        WHERE collection_films.collectionId = :collectionId
        ORDER BY collection_films.addedAtMillis DESC
        """
    )
    fun observeCollectionFilms(collectionId: Long): Flow<List<FilmEntity>>
}
