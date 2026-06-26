package ru.ifedorov.domain.model

data class FilmCollection(
    val type: FilmCollectionType,
    val title: String,
    val films: List<Film>
)
