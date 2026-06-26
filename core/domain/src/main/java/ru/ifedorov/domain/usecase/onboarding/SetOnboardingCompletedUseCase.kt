package ru.ifedorov.domain.usecase.onboarding

import ru.ifedorov.domain.repository.OnboardingRepository

class SetOnboardingCompletedUseCase(
    private val onboardingRepository: OnboardingRepository
) {

    suspend operator fun invoke(isCompleted: Boolean) = onboardingRepository.setOnboardingCompleted(isCompleted)
}
