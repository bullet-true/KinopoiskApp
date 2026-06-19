package ru.ifedorov.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_collections")
data class UserCollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val createdAtMillis: Long
)
