package ru.ifedorov.domain.usecase.collection

import ru.ifedorov.domain.repository.CollectionRepository

class CreateCollectionUseCase(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(name: String) = collectionRepository.createCollection(name)
}
