package ru.ifedorov.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.ifedorov.database.model.FilmEntity

@Dao
interface FilmDao {

    @Upsert
    suspend fun upsertFilm(film: FilmEntity)

    @Upsert
    suspend fun upsertFilms(films: List<FilmEntity>)

    @Query("SELECT * FROM films WHERE kinopoiskId = :filmId")
    fun observeFilm(filmId: Int): Flow<FilmEntity?>
}
