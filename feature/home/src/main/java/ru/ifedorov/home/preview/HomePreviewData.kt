package ru.ifedorov.home.preview

import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.model.FilmCollectionType
import ru.ifedorov.domain.model.FilmType
import ru.ifedorov.home.HomeSectionUiModel

private const val PREVIEW_HOME_FILMS_COUNT = 8
private const val PREVIEW_WATCHED_FILM_INDEX = 1

internal val PreviewHomeFilms = List(PREVIEW_HOME_FILMS_COUNT) { index ->
    Film(
        kinopoiskId = index,
        title = "Близкие",
        originalTitle = "Close",
        posterUrl = null,
        posterUrlPreview = null,
        rating = 7.8,
        year = 2024,
        type = FilmType.FILM,
        genres = listOf("драма"),
        countries = listOf("Россия"),
        isWatched = index == PREVIEW_WATCHED_FILM_INDEX
    )
}

internal val PreviewHomeSection = HomeSectionUiModel(
    type = FilmCollectionType.SERIES,
    title = "Сериалы",
    films = PreviewHomeFilms
)
