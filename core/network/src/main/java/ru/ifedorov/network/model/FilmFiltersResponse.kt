package ru.ifedorov.network.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class FilmFiltersResponse(
    val genres: List<NetworkFilterGenre> = emptyList(),
    val countries: List<NetworkFilterCountry> = emptyList()
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class NetworkFilterGenre(
    val id: Int,
    val genre: String? = null
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class NetworkFilterCountry(
    val id: Int,
    val country: String? = null
)
