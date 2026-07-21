package ru.ifedorov.domain.usecase.film

import ru.ifedorov.domain.repository.FilmRepository
import javax.inject.Inject

class GetHomeCollectionsUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {

    suspend operator fun invoke() = filmRepository.getHomeCollections()
}
