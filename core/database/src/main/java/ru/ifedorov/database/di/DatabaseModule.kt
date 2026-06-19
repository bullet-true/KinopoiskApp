package ru.ifedorov.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ifedorov.database.KinopoiskDatabase
import ru.ifedorov.database.dao.FilmDao
import ru.ifedorov.database.dao.FilmHistoryDao
import ru.ifedorov.database.dao.UserCollectionDao
import ru.ifedorov.database.dao.UserFilmStateDao
import javax.inject.Singleton

private const val DATABASE_NAME = "kinopoisk.db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideKinopoiskDatabase(
        @ApplicationContext context: Context
    ): KinopoiskDatabase = Room.databaseBuilder(
        context = context,
        klass = KinopoiskDatabase::class.java,
        name = DATABASE_NAME
    ).build()

    @Provides
    fun provideFilmDao(database: KinopoiskDatabase): FilmDao = database.filmDao()

    @Provides
    fun provideUserFilmStateDao(database: KinopoiskDatabase): UserFilmStateDao =
        database.userFilmStateDao()

    @Provides
    fun provideUserCollectionDao(database: KinopoiskDatabase): UserCollectionDao =
        database.userCollectionDao()

    @Provides
    fun provideFilmHistoryDao(database: KinopoiskDatabase): FilmHistoryDao =
        database.filmHistoryDao()
}
