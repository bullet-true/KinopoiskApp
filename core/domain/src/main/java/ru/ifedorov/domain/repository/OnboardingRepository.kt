package ru.ifedorov.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ifedorov.common.AppResult

interface OnboardingRepository {

    val isOnboardingCompleted: Flow<Boolean>

    suspend fun setOnboardingCompleted(isCompleted: Boolean): AppResult<Unit>
}
