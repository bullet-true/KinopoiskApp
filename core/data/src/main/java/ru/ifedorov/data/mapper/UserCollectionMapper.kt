package ru.ifedorov.data.mapper

import ru.ifedorov.database.model.UserCollectionEntity
import ru.ifedorov.domain.model.UserCollection

internal fun UserCollectionEntity.toDomainCollection(): UserCollection {
    return UserCollection(
        id = id,
        name = name
    )
}
