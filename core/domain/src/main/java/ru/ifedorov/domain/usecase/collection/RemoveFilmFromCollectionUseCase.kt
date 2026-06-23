package ru.ifedorov.domain.usecase.collection

import ru.ifedorov.domain.repository.CollectionRepository

class RemoveFilmFromCollectionUseCase(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(collectionId: Long, filmId: Int) =
        collectionRepository.removeFilmFromCollection(collectionId = collectionId, filmId = filmId)
}
