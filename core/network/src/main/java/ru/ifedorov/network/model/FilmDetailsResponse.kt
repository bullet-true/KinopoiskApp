package ru.ifedorov.network.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class FilmDetailsResponse(
    val kinopoiskId: Int,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val nameOriginal: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
    val ratingKinopoisk: Double? = null,
    val year: Int? = null,
    val filmLength: Int? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    val ratingAgeLimits: String? = null,
    val type: String? = null,
    val countries: List<NetworkCountry> = emptyList(),
    val genres: List<NetworkGenre> = emptyList()
)
