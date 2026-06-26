package ru.ifedorov.domain.model

data class Person(
    val personId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String?,
    val profession: String?,
    val bestFilms: List<Film> = emptyList()
)
