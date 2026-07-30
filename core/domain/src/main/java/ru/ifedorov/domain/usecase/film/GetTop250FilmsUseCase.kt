package ru.ifedorov.domain.usecase.film

import ru.ifedorov.domain.repository.FilmRepository
import javax.inject.Inject

class GetTop250FilmsUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {

    suspend operator fun invoke() = filmRepository.getTop250Films()
}
