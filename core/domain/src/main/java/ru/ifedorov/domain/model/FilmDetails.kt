package ru.ifedorov.domain.model

data class FilmDetails(
    val film: Film,
    val description: String?,
    val shortDescription: String?,
    val ratingAgeLimits: String?,
    val durationMinutes: Int?
)
