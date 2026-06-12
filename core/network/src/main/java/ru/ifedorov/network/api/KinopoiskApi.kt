package ru.ifedorov.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.ifedorov.network.model.FilmFiltersResponse
import ru.ifedorov.network.model.FilmSearchResponse
import ru.ifedorov.network.model.FilmsCollectionResponse
import ru.ifedorov.network.model.PremieresResponse

interface KinopoiskApi {

    @GET("api/v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): PremieresResponse

    @GET("api/v2.2/films/collections")
    suspend fun getFilmCollection(
        @Query("type") type: String,
        @Query("page") page: Int = DEFAULT_PAGE
    ): FilmsCollectionResponse

    @GET("api/v2.2/films")
    suspend fun getFilms(
        @Query("countries") countryIds: List<Int>? = null,
        @Query("genres") genreIds: List<Int>? = null,
        @Query("order") order: String = DEFAULT_ORDER,
        @Query("type") type: String = DEFAULT_FILM_TYPE,
        @Query("ratingFrom") ratingFrom: Int = MIN_RATING,
        @Query("ratingTo") ratingTo: Int = MAX_RATING,
        @Query("yearFrom") yearFrom: Int = MIN_YEAR,
        @Query("yearTo") yearTo: Int = MAX_YEAR,
        @Query("keyword") keyword: String? = null,
        @Query("page") page: Int = DEFAULT_PAGE
    ): FilmSearchResponse

    @GET("api/v2.2/films/filters")
    suspend fun getFilmFilters(): FilmFiltersResponse

    private companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_ORDER = "RATING"
        const val DEFAULT_FILM_TYPE = "ALL"
        const val MIN_RATING = 0
        const val MAX_RATING = 10
        const val MIN_YEAR = 1895
        const val MAX_YEAR = 2026
    }
}
