package ru.ifedorov.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ifedorov.common.AppResult
import ru.ifedorov.domain.usecase.film.GetHomeCollectionsUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeCollections()
    }

    fun onRetryClick() {
        loadHomeCollections()
    }

    private fun loadHomeCollections() {
        viewModelScope.launch {
            _uiState.update { HomeUiState.Loading }

            when (val result = getHomeCollectionsUseCase()) {
                is AppResult.Success -> _uiState.update {
                    HomeUiState.Content(sections = result.data.toHomeSections())
                }

                is AppResult.Error -> _uiState.update {
                    HomeUiState.Error(message = result.error.toHomeErrorMessage())
                }
            }
        }
    }
}
