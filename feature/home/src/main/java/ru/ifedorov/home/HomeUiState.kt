package ru.ifedorov.home

import ru.ifedorov.common.AppError
import ru.ifedorov.domain.model.FilmCollection

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Content(val sections: List<HomeSectionUiModel>) : HomeUiState

    data class Error(val message: String) : HomeUiState
}

data class HomeSectionUiModel(
    val title: String,
    val filmsCount: Int
)

internal fun List<FilmCollection>.toHomeSections(): List<HomeSectionUiModel> =
    map { collection ->
        HomeSectionUiModel(
            title = collection.title,
            filmsCount = collection.films.size
        )
    }

internal fun AppError.toHomeErrorMessage(): String = when (this) {
    AppError.Network -> "Не удалось загрузить данные. Проверьте подключение к интернету."
    AppError.Unauthorized -> "Не удалось загрузить данные. Проверьте API-ключ."
    AppError.NotFound -> "Подборки для главной не найдены."
    AppError.Unknown -> "Не удалось загрузить главную. Попробуйте ещё раз."
}
