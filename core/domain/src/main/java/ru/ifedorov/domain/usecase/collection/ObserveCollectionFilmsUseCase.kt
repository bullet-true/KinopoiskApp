package ru.ifedorov.domain.usecase.collection

import ru.ifedorov.domain.repository.CollectionRepository

class ObserveCollectionFilmsUseCase(
    private val collectionRepository: CollectionRepository
) {

    operator fun invoke(collectionId: Long) = collectionRepository.observeCollectionFilms(collectionId)
}
