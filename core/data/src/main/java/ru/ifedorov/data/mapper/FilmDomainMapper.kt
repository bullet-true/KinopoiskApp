package ru.ifedorov.data.mapper

import ru.ifedorov.data.model.FilmQuery
import ru.ifedorov.database.model.FilmEntity
import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.model.FilmCollection
import ru.ifedorov.domain.model.FilmCollectionType
import ru.ifedorov.domain.model.FilmDetails
import ru.ifedorov.domain.model.FilmFilters
import ru.ifedorov.domain.model.FilmSearchQuery
import ru.ifedorov.domain.model.FilmType
import ru.ifedorov.domain.model.FilterOption
import ru.ifedorov.network.model.FilmDetailsResponse
import ru.ifedorov.network.model.FilmFiltersResponse
import ru.ifedorov.network.model.NetworkCollectionFilm
import ru.ifedorov.network.model.NetworkCountry
import ru.ifedorov.network.model.NetworkGenre
import ru.ifedorov.network.model.NetworkPremiereFilm
import ru.ifedorov.network.model.NetworkSearchFilm

private const val DEFAULT_TITLE = "Без названия"

internal fun NetworkPremiereFilm.toDomainFilm(): Film {
    return Film(
        kinopoiskId = kinopoiskId,
        title = nameRu.orTitleFallback(nameEn),
        originalTitle = nameEn,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        rating = null,
        year = year,
        type = FilmType.UNKNOWN,
        genres = genres.toGenreNames(),
        countries = countries.toCountryNames()
    )
}

internal fun NetworkCollectionFilm.toDomainFilm(): Film {
    return Film(
        kinopoiskId = kinopoiskId,
        title = nameRu.orTitleFallback(nameOriginal ?: nameEn),
        originalTitle = nameOriginal ?: nameEn,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        rating = ratingKinopoisk,
        year = year,
        type = type.toFilmType(),
        genres = genres.toGenreNames(),
        countries = countries.toCountryNames()
    )
}

internal fun NetworkSearchFilm.toDomainFilm(): Film {
    return Film(
        kinopoiskId = kinopoiskId,
        title = nameRu.orTitleFallback(nameOriginal ?: nameEn),
        originalTitle = nameOriginal ?: nameEn,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        rating = ratingKinopoisk,
        year = year,
        type = type.toFilmType(),
        genres = genres.toGenreNames(),
        countries = countries.toCountryNames()
    )
}

internal fun FilmDetailsResponse.toDomainDetails(): FilmDetails {
    return FilmDetails(
        film = Film(
            kinopoiskId = kinopoiskId,
            title = nameRu.orTitleFallback(nameOriginal ?: nameEn),
            originalTitle = nameOriginal ?: nameEn,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            rating = ratingKinopoisk,
            year = year,
            type = type.toFilmType(),
            genres = genres.toGenreNames(),
            countries = countries.toCountryNames()
        ),
        description = description,
        shortDescription = shortDescription,
        ratingAgeLimits = ratingAgeLimits,
        durationMinutes = filmLength
    )
}

internal fun FilmEntity.toDomainFilm(): Film {
    return Film(
        kinopoiskId = kinopoiskId,
        title = nameRu.orTitleFallback(nameOriginal ?: nameEn),
        originalTitle = nameOriginal ?: nameEn,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        rating = ratingKinopoisk,
        year = year,
        type = type.toFilmType(),
        genres = listOfNotNull(genre),
        countries = listOfNotNull(country)
    )
}

internal fun Film.toFilmEntity(): FilmEntity {
    return FilmEntity(
        kinopoiskId = kinopoiskId,
        nameRu = title,
        nameEn = null,
        nameOriginal = originalTitle,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        ratingKinopoisk = rating,
        year = year,
        type = type.toApiValueOrNull(),
        genre = genres.firstOrNull(),
        country = countries.firstOrNull()
    )
}

internal fun FilmSearchQuery.toDataQuery(): FilmQuery {
    return FilmQuery(
        countryIds = countryIds,
        genreIds = genreIds,
        order = order.toApiValue(),
        type = type.toApiValueOrAll(),
        ratingFrom = ratingFrom,
        ratingTo = ratingTo,
        yearFrom = yearFrom,
        yearTo = yearTo,
        keyword = keyword,
        page = page
    )
}

internal fun FilmFiltersResponse.toDomainFilters(): FilmFilters {
    return FilmFilters(
        countries = countries.map { FilterOption(id = it.id, name = it.country.orEmpty()) },
        genres = genres.map { FilterOption(id = it.id, name = it.genre.orEmpty()) }
    )
}

internal fun List<NetworkCollectionFilm>.toDomainCollection(
    type: FilmCollectionType,
    title: String
): FilmCollection = FilmCollection(
    type = type,
    title = title,
    films = map(NetworkCollectionFilm::toDomainFilm)
)

private fun String?.orTitleFallback(fallback: String?): String = when {
    !isNullOrBlank() -> this
    !fallback.isNullOrBlank() -> fallback
    else -> DEFAULT_TITLE
}

private fun List<NetworkCountry>.toCountryNames(): List<String> = mapNotNull { it.country }

private fun List<NetworkGenre>.toGenreNames(): List<String> = mapNotNull { it.genre }
