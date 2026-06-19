package ru.ifedorov.data.mapper

import ru.ifedorov.database.model.FilmEntity
import ru.ifedorov.network.model.NetworkCollectionFilm
import ru.ifedorov.network.model.NetworkCountry
import ru.ifedorov.network.model.NetworkGenre
import ru.ifedorov.network.model.NetworkPremiereFilm
import ru.ifedorov.network.model.NetworkSearchFilm

internal fun NetworkPremiereFilm.toFilmEntity(): FilmEntity {
    return FilmEntity(
        kinopoiskId = kinopoiskId,
        nameRu = nameRu,
        nameEn = nameEn,
        nameOriginal = null,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        ratingKinopoisk = null,
        year = year,
        type = null,
        genre = genres.firstGenreName(),
        country = countries.firstCountryName()
    )
}

internal fun NetworkCollectionFilm.toFilmEntity(): FilmEntity {
    return FilmEntity(
        kinopoiskId = kinopoiskId,
        nameRu = nameRu,
        nameEn = nameEn,
        nameOriginal = nameOriginal,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        ratingKinopoisk = ratingKinopoisk,
        year = year,
        type = type,
        genre = genres.firstGenreName(),
        country = countries.firstCountryName()
    )
}

internal fun NetworkSearchFilm.toFilmEntity(): FilmEntity {
    return FilmEntity(
        kinopoiskId = kinopoiskId,
        nameRu = nameRu,
        nameEn = nameEn,
        nameOriginal = nameOriginal,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        ratingKinopoisk = ratingKinopoisk,
        year = year,
        type = type,
        genre = genres.firstGenreName(),
        country = countries.firstCountryName()
    )
}

private fun List<NetworkCountry>.firstCountryName(): String? = firstOrNull()?.country

private fun List<NetworkGenre>.firstGenreName(): String? = firstOrNull()?.genre
