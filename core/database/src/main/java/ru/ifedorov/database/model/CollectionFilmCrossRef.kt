package ru.ifedorov.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "collection_films",
    primaryKeys = ["collectionId", "filmId"],
    foreignKeys = [
        ForeignKey(
            entity = UserCollectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["collectionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FilmEntity::class,
            parentColumns = ["kinopoiskId"],
            childColumns = ["filmId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["collectionId"]),
        Index(value = ["filmId"])
    ]
)
data class CollectionFilmCrossRef(
    val collectionId: Long,
    val filmId: Int,
    val addedAtMillis: Long
)
