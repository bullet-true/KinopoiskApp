package ru.ifedorov.domain.usecase.onboarding

import ru.ifedorov.domain.repository.OnboardingRepository

class ObserveOnboardingCompletedUseCase(
    private val onboardingRepository: OnboardingRepository
) {

    operator fun invoke() = onboardingRepository.isOnboardingCompleted
}
