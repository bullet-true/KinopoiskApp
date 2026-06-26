package ru.ifedorov.domain.model

data class UserCollection(
    val id: Long,
    val name: String,
    val films: List<Film> = emptyList()
)
