package ru.ifedorov.data.source

import kotlinx.coroutines.flow.Flow
import ru.ifedorov.database.dao.FilmHistoryDao
import ru.ifedorov.database.model.FilmEntity
import ru.ifedorov.database.model.InterestedFilmEntity
import javax.inject.Inject

internal class LocalHistoryDataSource @Inject constructor(
    private val filmHistoryDao: FilmHistoryDao
) {

    suspend fun upsertInterestedFilm(interestedFilm: InterestedFilmEntity) {
        filmHistoryDao.upsertInterestedFilm(interestedFilm)
    }

    fun observeInterestedFilms(): Flow<List<FilmEntity>> = filmHistoryDao.observeInterestedFilms()

    suspend fun clearInterestedFilms() {
        filmHistoryDao.clearInterestedFilms()
    }
}
