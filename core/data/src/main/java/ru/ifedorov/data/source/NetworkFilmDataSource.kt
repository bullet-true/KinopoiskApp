package ru.ifedorov.data.source

import ru.ifedorov.common.AppResult
import ru.ifedorov.data.model.FilmQuery
import ru.ifedorov.data.util.safeDataCall
import ru.ifedorov.network.api.KinopoiskApi
import ru.ifedorov.network.model.FilmDetailsResponse
import ru.ifedorov.network.model.FilmFiltersResponse
import ru.ifedorov.network.model.FilmSearchResponse
import ru.ifedorov.network.model.FilmsCollectionResponse
import ru.ifedorov.network.model.PremieresResponse
import javax.inject.Inject

internal class NetworkFilmDataSource @Inject constructor(
    private val kinopoiskApi: KinopoiskApi
) {

    suspend fun getFilmDetails(filmId: Int): AppResult<FilmDetailsResponse> = safeDataCall {
        kinopoiskApi.getFilmDetails(filmId = filmId)
    }

    suspend fun getPremieres(year: Int, month: String): AppResult<PremieresResponse> =
        safeDataCall {
            kinopoiskApi.getPremieres(year = year, month = month)
        }

    suspend fun getFilmCollection(type: String, page: Int): AppResult<FilmsCollectionResponse> =
        safeDataCall {
            kinopoiskApi.getFilmCollection(type = type, page = page)
        }

    suspend fun getFilms(query: FilmQuery): AppResult<FilmSearchResponse> = safeDataCall {
        kinopoiskApi.getFilms(
            countryIds = query.countryIds,
            genreIds = query.genreIds,
            order = query.order,
            type = query.type,
            ratingFrom = query.ratingFrom,
            ratingTo = query.ratingTo,
            yearFrom = query.yearFrom,
            yearTo = query.yearTo,
            keyword = query.keyword,
            page = query.page
        )
    }

    suspend fun getFilmFilters(): AppResult<FilmFiltersResponse> = safeDataCall {
        kinopoiskApi.getFilmFilters()
    }
}
