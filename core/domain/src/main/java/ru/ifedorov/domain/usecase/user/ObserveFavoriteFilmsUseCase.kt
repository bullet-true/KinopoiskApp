package ru.ifedorov.domain.usecase.user

import ru.ifedorov.domain.repository.UserFilmRepository

class ObserveFavoriteFilmsUseCase(
    private val userFilmRepository: UserFilmRepository
) {

    operator fun invoke() = userFilmRepository.observeFavoriteFilms()
}
