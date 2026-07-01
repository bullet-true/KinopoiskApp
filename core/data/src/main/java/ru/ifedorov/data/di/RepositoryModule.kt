package ru.ifedorov.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ifedorov.data.repository.CollectionRepositoryImpl
import ru.ifedorov.data.repository.FilmRepositoryImpl
import ru.ifedorov.data.repository.OnboardingRepositoryImpl
import ru.ifedorov.data.repository.UserFilmRepositoryImpl
import ru.ifedorov.domain.repository.CollectionRepository
import ru.ifedorov.domain.repository.FilmRepository
import ru.ifedorov.domain.repository.OnboardingRepository
import ru.ifedorov.domain.repository.UserFilmRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindFilmRepository(repository: FilmRepositoryImpl): FilmRepository

    @Binds
    @Singleton
    fun bindUserFilmRepository(repository: UserFilmRepositoryImpl): UserFilmRepository

    @Binds
    @Singleton
    fun bindCollectionRepository(repository: CollectionRepositoryImpl): CollectionRepository

    @Binds
    @Singleton
    fun bindOnboardingRepository(repository: OnboardingRepositoryImpl): OnboardingRepository
}
