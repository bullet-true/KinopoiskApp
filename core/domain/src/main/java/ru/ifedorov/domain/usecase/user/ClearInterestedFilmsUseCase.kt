package ru.ifedorov.domain.usecase.user

import ru.ifedorov.domain.repository.UserFilmRepository

class ClearInterestedFilmsUseCase(
    private val userFilmRepository: UserFilmRepository
) {

    suspend operator fun invoke() = userFilmRepository.clearInterestedFilms()
}
