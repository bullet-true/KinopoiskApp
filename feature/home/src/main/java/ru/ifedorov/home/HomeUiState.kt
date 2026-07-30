package ru.ifedorov.home

import ru.ifedorov.common.AppError
import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.model.FilmCollection
import ru.ifedorov.domain.model.FilmCollectionType

data class HomeUiState(
    val premieres: HomeSectionState = HomeSectionState.Loading(title = HOME_SECTION_TITLE_PREMIERES),
    val popular: HomeSectionState = HomeSectionState.Loading(title = HOME_SECTION_TITLE_POPULAR),
    val top250: HomeSectionState = HomeSectionState.Loading(title = HOME_SECTION_TITLE_TOP_250),
    val series: HomeSectionState = HomeSectionState.Loading(title = HOME_SECTION_TITLE_SERIES)
) {
    val sections: List<HomeSectionState>
        get() = listOf(premieres, popular, top250, series)
}

sealed interface HomeSectionState {
    data class Loading(
        val title: String
    ) : HomeSectionState

    data class Content(
        val section: HomeSectionUiModel
    ) : HomeSectionState

    data class Error(
        val title: String,
        val message: String
    ) : HomeSectionState

    data class Empty(
        val title: String
    ) : HomeSectionState
}

data class HomeSectionUiModel(
    val type: FilmCollectionType,
    val title: String,
    val films: List<Film>
) {
    val filmsCount: Int = films.size
}

internal fun FilmCollection.toHomeSection(): HomeSectionUiModel =
    HomeSectionUiModel(
        type = type,
        title = title,
        films = films
    )

internal fun List<Film>.toPremieresSection(): HomeSectionUiModel =
    HomeSectionUiModel(
        type = FilmCollectionType.PREMIERES,
        title = HOME_SECTION_TITLE_PREMIERES,
        films = this
    )

internal fun AppError.toHomeErrorMessage(): String = when (this) {
    AppError.Network -> "Проверьте подключение к интернету."
    AppError.Unauthorized -> "Проверьте API-ключ."
    AppError.NotFound -> "Подборка не найдена."
    AppError.Unknown -> "Попробуйте загрузить секцию ещё раз."
}
