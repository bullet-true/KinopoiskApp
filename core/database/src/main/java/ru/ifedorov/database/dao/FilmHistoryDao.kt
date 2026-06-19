package ru.ifedorov.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.ifedorov.database.model.FilmEntity
import ru.ifedorov.database.model.InterestedFilmEntity

@Dao
interface FilmHistoryDao {

    @Upsert
    suspend fun upsertInterestedFilm(interestedFilm: InterestedFilmEntity)

    @Query(
        """
        SELECT films.* FROM films
        INNER JOIN interested_films ON films.kinopoiskId = interested_films.filmId
        ORDER BY interested_films.viewedAtMillis DESC
        """
    )
    fun observeInterestedFilms(): Flow<List<FilmEntity>>

    @Query("DELETE FROM interested_films")
    suspend fun clearInterestedFilms()
}
