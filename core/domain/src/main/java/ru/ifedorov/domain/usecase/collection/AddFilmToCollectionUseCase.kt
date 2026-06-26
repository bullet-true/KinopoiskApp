package ru.ifedorov.domain.usecase.collection

import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.repository.CollectionRepository

class AddFilmToCollectionUseCase(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(collectionId: Long, film: Film) =
        collectionRepository.addFilmToCollection(collectionId = collectionId, film = film)
}
