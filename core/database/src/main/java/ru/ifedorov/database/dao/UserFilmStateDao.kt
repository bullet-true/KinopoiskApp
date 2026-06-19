package ru.ifedorov.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.ifedorov.database.model.FilmEntity
import ru.ifedorov.database.model.UserFilmStateEntity

@Dao
interface UserFilmStateDao {

    @Upsert
    suspend fun upsertUserFilmState(state: UserFilmStateEntity)

    @Query("SELECT * FROM user_film_states WHERE filmId = :filmId")
    fun observeUserFilmState(filmId: Int): Flow<UserFilmStateEntity?>

    @Query(
        """
        SELECT films.* FROM films
        INNER JOIN user_film_states ON films.kinopoiskId = user_film_states.filmId
        WHERE user_film_states.isFavorite = 1
        ORDER BY user_film_states.updatedAtMillis DESC
        """
    )
    fun observeFavoriteFilms(): Flow<List<FilmEntity>>

    @Query(
        """
        SELECT films.* FROM films
        INNER JOIN user_film_states ON films.kinopoiskId = user_film_states.filmId
        WHERE user_film_states.isWantToWatch = 1
        ORDER BY user_film_states.updatedAtMillis DESC
        """
    )
    fun observeWantToWatchFilms(): Flow<List<FilmEntity>>

    @Query(
        """
        SELECT films.* FROM films
        INNER JOIN user_film_states ON films.kinopoiskId = user_film_states.filmId
        WHERE user_film_states.isWatched = 1
        ORDER BY user_film_states.updatedAtMillis DESC
        """
    )
    fun observeWatchedFilms(): Flow<List<FilmEntity>>
}
