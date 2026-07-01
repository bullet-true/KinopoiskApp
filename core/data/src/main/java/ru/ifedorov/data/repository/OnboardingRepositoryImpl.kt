package ru.ifedorov.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ifedorov.common.AppResult
import ru.ifedorov.datastore.AppPreferencesDataSource
import ru.ifedorov.domain.repository.OnboardingRepository
import javax.inject.Inject

internal class OnboardingRepositoryImpl @Inject constructor(
    private val appPreferencesDataSource: AppPreferencesDataSource
) : OnboardingRepository {

    override val isOnboardingCompleted: Flow<Boolean> = appPreferencesDataSource.isOnboardingCompleted

    override suspend fun setOnboardingCompleted(isCompleted: Boolean): AppResult<Unit> {
        appPreferencesDataSource.setOnboardingCompleted(isCompleted)
        return AppResult.Success(Unit)
    }
}
