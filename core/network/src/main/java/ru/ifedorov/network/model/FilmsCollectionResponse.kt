package ru.ifedorov.network.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class FilmsCollectionResponse(
    val total: Int = 0,
    val totalPages: Int = 0,
    val items: List<NetworkCollectionFilm> = emptyList()
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class NetworkCollectionFilm(
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
    val posterUrlPreview: String? = null,
    val coverUrl: String? = null,
    val logoUrl: String? = null,
    val description: String? = null,
    val ratingAgeLimits: String? = null
)
