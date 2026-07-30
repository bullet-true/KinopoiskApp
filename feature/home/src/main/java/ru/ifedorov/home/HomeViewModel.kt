package ru.ifedorov.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ifedorov.common.AppError
import ru.ifedorov.common.AppResult
import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.model.FilmCollection
import ru.ifedorov.domain.usecase.film.GetPopularFilmsUseCase
import ru.ifedorov.domain.usecase.film.GetPremieresUseCase
import ru.ifedorov.domain.usecase.film.GetSeriesUseCase
import ru.ifedorov.domain.usecase.film.GetTop250FilmsUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPremieresUseCase: GetPremieresUseCase,
    private val getPopularFilmsUseCase: GetPopularFilmsUseCase,
    private val getTop250FilmsUseCase: GetTop250FilmsUseCase,
    private val getSeriesUseCase: GetSeriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadAllSections()
    }

    fun onRetryClick() {
        loadAllSections()
    }

    private fun loadAllSections() {
        loadPremieres()
        loadPopular()
        loadTop250()
        loadSeries()
    }

    private fun loadPremieres() {
        viewModelScope.launch {
            _uiState.update { it.copy(premieres = HomeSectionState.Loading(title = HOME_SECTION_TITLE_PREMIERES)) }

            val sectionState = when (val result = getPremieresUseCase()) {
                is AppResult.Success -> result.data.toSectionState(title = HOME_SECTION_TITLE_PREMIERES)
                is AppResult.Error -> result.error.toSectionError(title = HOME_SECTION_TITLE_PREMIERES)
            }

            _uiState.update { it.copy(premieres = sectionState) }
        }
    }

    private fun loadPopular() {
        viewModelScope.launch {
            _uiState.update { it.copy(popular = HomeSectionState.Loading(title = HOME_SECTION_TITLE_POPULAR)) }

            val sectionState =
                getPopularFilmsUseCase().toSectionState(title = HOME_SECTION_TITLE_POPULAR)

            _uiState.update { it.copy(popular = sectionState) }
        }
    }

    private fun loadTop250() {
        viewModelScope.launch {
            _uiState.update { it.copy(top250 = HomeSectionState.Loading(title = HOME_SECTION_TITLE_TOP_250)) }

            val sectionState =
                getTop250FilmsUseCase().toSectionState(title = HOME_SECTION_TITLE_TOP_250)

            _uiState.update { it.copy(top250 = sectionState) }
        }
    }

    private fun loadSeries() {
        viewModelScope.launch {
            _uiState.update { it.copy(series = HomeSectionState.Loading(title = HOME_SECTION_TITLE_SERIES)) }

            val sectionState = getSeriesUseCase().toSectionState(title = HOME_SECTION_TITLE_SERIES)

            _uiState.update { it.copy(series = sectionState) }
        }
    }

    private fun AppResult<FilmCollection>.toSectionState(title: String): HomeSectionState =
        when (this) {
            is AppResult.Success -> data.toHomeSection().toSectionState()
            is AppResult.Error -> error.toSectionError(title = title)
        }

    private fun List<Film>.toSectionState(title: String): HomeSectionState =
        toPremieresSection().copy(title = title).toSectionState()

    private fun HomeSectionUiModel.toSectionState(): HomeSectionState =
        if (films.isEmpty()) {
            HomeSectionState.Empty(title = title)
        } else {
            HomeSectionState.Content(section = this)
        }

    private fun AppError.toSectionError(title: String): HomeSectionState.Error =
        HomeSectionState.Error(
            title = title,
            message = toHomeErrorMessage()
        )
}
