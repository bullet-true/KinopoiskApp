package ru.ifedorov.domain.model

data class Film(
    val kinopoiskId: Int,
    val title: String,
    val originalTitle: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val rating: Double?,
    val year: Int?,
    val type: FilmType,
    val genres: List<String>,
    val countries: List<String>,
    val isFavorite: Boolean = false,
    val isWantToWatch: Boolean = false,
    val isWatched: Boolean = false
)
