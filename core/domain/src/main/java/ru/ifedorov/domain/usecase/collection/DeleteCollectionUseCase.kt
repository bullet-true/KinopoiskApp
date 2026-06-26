package ru.ifedorov.domain.usecase.collection

import ru.ifedorov.domain.repository.CollectionRepository

class DeleteCollectionUseCase(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(collectionId: Long) = collectionRepository.deleteCollection(collectionId)
}
