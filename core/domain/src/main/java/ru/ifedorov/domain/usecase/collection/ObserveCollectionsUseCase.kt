package ru.ifedorov.domain.usecase.collection

import ru.ifedorov.domain.repository.CollectionRepository

class ObserveCollectionsUseCase(
    private val collectionRepository: CollectionRepository
) {

    operator fun invoke() = collectionRepository.observeCollections()
}
