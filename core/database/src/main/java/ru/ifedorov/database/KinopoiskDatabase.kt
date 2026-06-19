package ru.ifedorov.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ifedorov.database.dao.FilmDao
import ru.ifedorov.database.dao.FilmHistoryDao
import ru.ifedorov.database.dao.UserCollectionDao
import ru.ifedorov.database.dao.UserFilmStateDao
import ru.ifedorov.database.model.CollectionFilmCrossRef
import ru.ifedorov.database.model.FilmEntity
import ru.ifedorov.database.model.InterestedFilmEntity
import ru.ifedorov.database.model.UserCollectionEntity
import ru.ifedorov.database.model.UserFilmStateEntity

@Database(
    entities = [
        FilmEntity::class,
        UserFilmStateEntity::class,
        UserCollectionEntity::class,
        CollectionFilmCrossRef::class,
        InterestedFilmEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KinopoiskDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao

    abstract fun userFilmStateDao(): UserFilmStateDao

    abstract fun userCollectionDao(): UserCollectionDao

    abstract fun filmHistoryDao(): FilmHistoryDao
}
