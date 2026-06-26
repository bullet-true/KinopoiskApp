package ru.ifedorov.domain.usecase.film

import ru.ifedorov.domain.repository.FilmRepository

class GetFilmFiltersUseCase(
    private val filmRepository: FilmRepository
) {

    suspend operator fun invoke() = filmRepository.getFilmFilters()
}
