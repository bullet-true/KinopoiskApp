package ru.ifedorov.domain.model

data class FilmSearchQuery(
    val countryIds: List<Int>? = null,
    val genreIds: List<Int>? = null,
    val order: FilmSortOrder = FilmSortOrder.RATING,
    val type: FilmType = FilmType.ALL,
    val ratingFrom: Int = MIN_RATING,
    val ratingTo: Int = MAX_RATING,
    val yearFrom: Int = MIN_YEAR,
    val yearTo: Int = MAX_YEAR,
    val keyword: String? = null,
    val page: Int = DEFAULT_PAGE
) {
    private companion object {
        const val DEFAULT_PAGE = 1
        const val MIN_RATING = 0
        const val MAX_RATING = 10
        const val MIN_YEAR = 1895
        const val MAX_YEAR = 2026
    }
}
