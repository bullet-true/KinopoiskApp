package ru.ifedorov.network.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class NetworkCountry(
    val country: String? = null
)
