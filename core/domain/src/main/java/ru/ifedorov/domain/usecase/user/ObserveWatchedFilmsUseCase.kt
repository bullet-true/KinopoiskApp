package ru.ifedorov.domain.usecase.user

import ru.ifedorov.domain.repository.UserFilmRepository

class ObserveWatchedFilmsUseCase(
    private val userFilmRepository: UserFilmRepository
) {

    operator fun invoke() = userFilmRepository.observeWatchedFilms()
}
