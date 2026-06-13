package ru.ifedorov.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "interested_films",
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
data class InterestedFilmEntity(
    @PrimaryKey
    val filmId: Int,
    val viewedAtMillis: Long
)
