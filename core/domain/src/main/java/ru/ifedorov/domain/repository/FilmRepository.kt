package ru.ifedorov.domain.repository

import ru.ifedorov.common.AppResult
import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.model.FilmCollection
import ru.ifedorov.domain.model.FilmDetails
import ru.ifedorov.domain.model.FilmFilters
import ru.ifedorov.domain.model.FilmSearchQuery
import ru.ifedorov.domain.model.Person

interface FilmRepository {

    suspend fun getPremieres(): AppResult<List<Film>>

    suspend fun getPopularFilms(): AppResult<FilmCollection>

    suspend fun getTop250Films(): AppResult<FilmCollection>

    suspend fun getSeries(): AppResult<FilmCollection>

    suspend fun getFilmDetails(filmId: Int): AppResult<FilmDetails>

    suspend fun searchFilms(query: FilmSearchQuery): AppResult<List<Film>>

    suspend fun getFilmFilters(): AppResult<FilmFilters>

    suspend fun getPersonDetails(personId: Int): AppResult<Person>
}
