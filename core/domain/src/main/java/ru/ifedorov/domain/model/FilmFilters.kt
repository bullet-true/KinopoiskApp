package ru.ifedorov.domain.model

data class FilmFilters(
    val countries: List<FilterOption>,
    val genres: List<FilterOption>
)
