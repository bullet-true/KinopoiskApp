package ru.ifedorov.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ifedorov.common.AppResult
import ru.ifedorov.domain.model.Film

interface UserFilmRepository {

    fun observeFavoriteFilms(): Flow<List<Film>>

    fun observeWantToWatchFilms(): Flow<List<Film>>

    fun observeWatchedFilms(): Flow<List<Film>>

    fun observeInterestedFilms(): Flow<List<Film>>

    suspend fun toggleFavorite(film: Film): AppResult<Unit>

    suspend fun toggleWantToWatch(film: Film): AppResult<Unit>

    suspend fun toggleWatched(film: Film): AppResult<Unit>

    suspend fun markInterested(film: Film): AppResult<Unit>

    suspend fun clearInterestedFilms(): AppResult<Unit>
}
