package ru.ifedorov.network.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class PremieresResponse(
    val total: Int = 0,
    val items: List<NetworkPremiereFilm> = emptyList()
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class NetworkPremiereFilm(
    val kinopoiskId: Int,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val year: Int? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
    val countries: List<NetworkCountry> = emptyList(),
    val genres: List<NetworkGenre> = emptyList(),
    val duration: Int? = null,
    val premiereRu: String? = null
)
