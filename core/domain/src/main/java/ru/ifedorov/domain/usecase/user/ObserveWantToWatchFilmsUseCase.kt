package ru.ifedorov.domain.usecase.user

import ru.ifedorov.domain.repository.UserFilmRepository

class ObserveWantToWatchFilmsUseCase(
    private val userFilmRepository: UserFilmRepository
) {

    operator fun invoke() = userFilmRepository.observeWantToWatchFilms()
}
