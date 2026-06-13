package ru.ifedorov.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_film_states",
    foreignKeys = [
        ForeignKey(
            entity = FilmEntity::class,
            parentColumns = ["kinopoiskId"],
            childColumns = ["filmId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["filmId"])
    ]
)
data class UserFilmStateEntity(
    @PrimaryKey
    val filmId: Int,
    val isFavorite: Boolean,
    val isWantToWatch: Boolean,
    val isWatched: Boolean,
    val updatedAtMillis: Long
)
