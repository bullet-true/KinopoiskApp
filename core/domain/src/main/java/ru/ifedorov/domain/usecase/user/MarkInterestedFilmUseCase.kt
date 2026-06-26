package ru.ifedorov.domain.usecase.user

import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.repository.UserFilmRepository

class MarkInterestedFilmUseCase(
    private val userFilmRepository: UserFilmRepository
) {

    suspend operator fun invoke(film: Film) = userFilmRepository.markInterested(film)
}
