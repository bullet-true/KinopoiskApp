package ru.ifedorov.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val ratingKinopoisk: Double?,
    val year: Int?,
    val type: String?,
    val genre: String?,
    val country: String?
)
