package ru.ifedorov.kinopoiskapp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.ifedorov.domain.usecase.onboarding.ObserveOnboardingCompletedUseCase
import javax.inject.Inject

private const val STARTUP_STATE_SUBSCRIPTION_TIMEOUT_MILLIS = 5_000L

@HiltViewModel
class KinopoiskAppViewModel @Inject constructor(
    observeOnboardingCompletedUseCase: ObserveOnboardingCompletedUseCase
) : ViewModel() {

    val uiState: StateFlow<KinopoiskAppUiState> = observeOnboardingCompletedUseCase()
        .map { isOnboardingCompleted ->
            if (isOnboardingCompleted) {
                KinopoiskAppUiState.Main
            } else {
                KinopoiskAppUiState.Onboarding
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STARTUP_STATE_SUBSCRIPTION_TIMEOUT_MILLIS),
            initialValue = KinopoiskAppUiState.Loading
        )
}

sealed interface KinopoiskAppUiState {
    data object Loading : KinopoiskAppUiState
    data object Onboarding : KinopoiskAppUiState
    data object Main : KinopoiskAppUiState
}
