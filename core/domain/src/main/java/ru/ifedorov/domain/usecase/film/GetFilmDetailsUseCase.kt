package ru.ifedorov.domain.usecase.film

import ru.ifedorov.domain.repository.FilmRepository

class GetFilmDetailsUseCase(
    private val filmRepository: FilmRepository
) {

    suspend operator fun invoke(filmId: Int) = filmRepository.getFilmDetails(filmId)
}
