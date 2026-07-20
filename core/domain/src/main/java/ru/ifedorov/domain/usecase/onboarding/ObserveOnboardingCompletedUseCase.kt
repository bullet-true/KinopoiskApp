package ru.ifedorov.domain.usecase.onboarding

import ru.ifedorov.domain.repository.OnboardingRepository
import javax.inject.Inject

class ObserveOnboardingCompletedUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {

    operator fun invoke() = onboardingRepository.isOnboardingCompleted
}
