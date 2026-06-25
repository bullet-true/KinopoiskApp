package ru.ifedorov.domain.usecase.user

import ru.ifedorov.domain.repository.UserFilmRepository

class ObserveInterestedFilmsUseCase(
    private val userFilmRepository: UserFilmRepository
) {

    operator fun invoke() = userFilmRepository.observeInterestedFilms()
}
