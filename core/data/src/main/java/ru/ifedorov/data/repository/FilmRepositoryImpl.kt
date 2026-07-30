package ru.ifedorov.data.repository

import ru.ifedorov.common.AppError
import ru.ifedorov.common.AppResult
import ru.ifedorov.data.mapper.mapSuccess
import ru.ifedorov.data.mapper.toDataQuery
import ru.ifedorov.data.mapper.toDomainCollection
import ru.ifedorov.data.mapper.toDomainFilm
import ru.ifedorov.data.mapper.toDomainFilters
import ru.ifedorov.data.source.NetworkFilmDataSource
import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.model.FilmCollection
import ru.ifedorov.domain.model.FilmCollectionType
import ru.ifedorov.domain.model.FilmDetails
import ru.ifedorov.domain.model.FilmFilters
import ru.ifedorov.domain.model.FilmSearchQuery
import ru.ifedorov.domain.model.Person
import ru.ifedorov.domain.repository.FilmRepository
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

private const val FIRST_PAGE = 1
private const val POPULAR_COLLECTION = "TOP_POPULAR_ALL"
private const val TOP_250_COLLECTION = "TOP_250_MOVIES"
private const val SERIES_COLLECTION = "POPULAR_SERIES"

internal class FilmRepositoryImpl @Inject constructor(
    private val networkFilmDataSource: NetworkFilmDataSource
) : FilmRepository {

    override suspend fun getPremieres(): AppResult<List<Film>> {
        val currentDate = LocalDate.now()
        val month = Month.of(currentDate.monthValue).name

        return networkFilmDataSource.getPremieres(year = currentDate.year, month = month)
            .mapSuccess { response -> response.items.map { it.toDomainFilm() } }
    }

    override suspend fun getPopularFilms(): AppResult<FilmCollection> {
        return getCollection(
            apiType = POPULAR_COLLECTION,
            domainType = FilmCollectionType.POPULAR,
            title = "Популярное"
        )
    }

    override suspend fun getTop250Films(): AppResult<FilmCollection> {
        return getCollection(
            apiType = TOP_250_COLLECTION,
            domainType = FilmCollectionType.TOP_250,
            title = "Топ-250"
        )
    }

    override suspend fun getSeries(): AppResult<FilmCollection> {
        return getCollection(
            apiType = SERIES_COLLECTION,
            domainType = FilmCollectionType.SERIES,
            title = "Сериалы"
        )
    }

    override suspend fun getFilmDetails(filmId: Int): AppResult<FilmDetails> =
        AppResult.Error(AppError.NotFound)

    override suspend fun searchFilms(query: FilmSearchQuery): AppResult<List<Film>> =
        networkFilmDataSource.getFilms(query.toDataQuery())
            .mapSuccess { response -> response.items.map { it.toDomainFilm() } }

    override suspend fun getFilmFilters(): AppResult<FilmFilters> =
        networkFilmDataSource.getFilmFilters()
            .mapSuccess { response -> response.toDomainFilters() }

    override suspend fun getPersonDetails(personId: Int): AppResult<Person> =
        AppResult.Error(AppError.NotFound)

    private suspend fun getCollection(
        apiType: String,
        domainType: FilmCollectionType,
        title: String
    ): AppResult<FilmCollection> {
        return networkFilmDataSource.getFilmCollection(type = apiType, page = FIRST_PAGE)
            .mapSuccess { response ->
                response.items.toDomainCollection(
                    type = domainType,
                    title = title
                )
            }
    }
}
