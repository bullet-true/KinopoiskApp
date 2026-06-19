package ru.ifedorov.network.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class FilmSearchResponse(
    val total: Int = 0,
    val totalPages: Int = 0,
    val items: List<NetworkSearchFilm> = emptyList()
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class NetworkSearchFilm(
    val kinopoiskId: Int,
    val imdbId: String? = null,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val nameOriginal: String? = null,
    val countries: List<NetworkCountry> = emptyList(),
    val genres: List<NetworkGenre> = emptyList(),
    val ratingKinopoisk: Double? = null,
    val ratingImdb: Double? = null,
    val year: Int? = null,
    val type: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null
)
