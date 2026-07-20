package ru.ifedorov.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ifedorov.common.AppResult
import ru.ifedorov.domain.usecase.onboarding.SetOnboardingCompletedUseCase
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _finishEvents = MutableSharedFlow<Unit>()
    val finishEvents: SharedFlow<Unit> = _finishEvents.asSharedFlow()

    fun onFinishClick() {
        if (_uiState.value.isCompleting) return

        viewModelScope.launch {
            _uiState.update { it.copy(isCompleting = true) }

            when (setOnboardingCompletedUseCase(true)) {
                is AppResult.Success -> _finishEvents.emit(Unit)
                is AppResult.Error -> _uiState.update { it.copy(isCompleting = false) }
            }
        }
    }
}

data class OnboardingUiState(
    val isCompleting: Boolean = false
)
