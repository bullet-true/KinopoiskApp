package ru.ifedorov.data.mapper

import ru.ifedorov.common.AppResult

internal inline fun <T, R> AppResult<T>.mapSuccess(transform: (T) -> R): AppResult<R> {
    return when (this) {
        is AppResult.Success -> AppResult.Success(transform(data))
        is AppResult.Error -> this
    }
}
