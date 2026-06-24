package ru.ifedorov.domain.usecase.film

import ru.ifedorov.domain.model.FilmSearchQuery
import ru.ifedorov.domain.repository.FilmRepository

class SearchFilmsUseCase(
    private val filmRepository: FilmRepository
) {

    suspend operator fun invoke(query: FilmSearchQuery) = filmRepository.searchFilms(query)
}
