package ru.ifedorov.domain.usecase.onboarding

import ru.ifedorov.domain.repository.OnboardingRepository
import javax.inject.Inject

class SetOnboardingCompletedUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {

    suspend operator fun invoke(isCompleted: Boolean) = onboardingRepository.setOnboardingCompleted(isCompleted)
}
