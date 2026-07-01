package ru.ifedorov.data.mapper

import ru.ifedorov.domain.model.FilmSortOrder
import ru.ifedorov.domain.model.FilmType

private const val API_FILM = "FILM"
private const val API_TV_SHOW = "TV_SHOW"
private const val API_TV_SERIES = "TV_SERIES"
private const val API_MINI_SERIES = "MINI_SERIES"
private const val API_VIDEO = "VIDEO"
private const val API_ALL = "ALL"
private const val API_RATING = "RATING"
private const val API_NUM_VOTE = "NUM_VOTE"
private const val API_YEAR = "YEAR"

internal fun String?.toFilmType(): FilmType = when (this) {
    API_FILM -> FilmType.FILM
    API_TV_SHOW -> FilmType.TV_SHOW
    API_TV_SERIES -> FilmType.TV_SERIES
    API_MINI_SERIES -> FilmType.MINI_SERIES
    API_VIDEO -> FilmType.VIDEO
    API_ALL -> FilmType.ALL
    else -> FilmType.UNKNOWN
}

internal fun FilmType.toApiValueOrAll(): String = when (this) {
    FilmType.FILM -> API_FILM
    FilmType.TV_SHOW -> API_TV_SHOW
    FilmType.TV_SERIES -> API_TV_SERIES
    FilmType.MINI_SERIES -> API_MINI_SERIES
    FilmType.ALL,
    FilmType.VIDEO,
    FilmType.UNKNOWN -> API_ALL
}

internal fun FilmType.toApiValueOrNull(): String? = when (this) {
    FilmType.FILM -> API_FILM
    FilmType.TV_SHOW -> API_TV_SHOW
    FilmType.TV_SERIES -> API_TV_SERIES
    FilmType.MINI_SERIES -> API_MINI_SERIES
    FilmType.VIDEO -> API_VIDEO
    FilmType.ALL,
    FilmType.UNKNOWN -> null
}

internal fun FilmSortOrder.toApiValue(): String = when (this) {
    FilmSortOrder.RATING -> API_RATING
    FilmSortOrder.NUM_VOTE -> API_NUM_VOTE
    FilmSortOrder.YEAR -> API_YEAR
}
