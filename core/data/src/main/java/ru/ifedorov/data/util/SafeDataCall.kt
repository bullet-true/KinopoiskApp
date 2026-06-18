package ru.ifedorov.data.util

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import ru.ifedorov.common.AppError
import ru.ifedorov.common.AppResult
import java.io.IOException

private const val HTTP_UNAUTHORIZED = 401
private const val HTTP_FORBIDDEN = 403
private const val HTTP_NOT_FOUND = 404

internal suspend fun <T> safeDataCall(block: suspend () -> T): AppResult<T> {
    return try {
        AppResult.Success(block())
    } catch (exception: IOException) {
        exception.toNetworkError()
    } catch (exception: HttpException) {
        AppResult.Error(exception.toAppError())
    } catch (exception: CancellationException) {
        throw exception
    } catch (exception: SerializationException) {
        exception.toUnknownError()
    }
}

private fun IOException.toNetworkError(): AppResult.Error = AppResult.Error(AppError.Network)

private fun SerializationException.toUnknownError(): AppResult.Error = AppResult.Error(AppError.Unknown)

private fun HttpException.toAppError(): AppError = when (code()) {
    HTTP_UNAUTHORIZED, HTTP_FORBIDDEN -> AppError.Unauthorized

    HTTP_NOT_FOUND -> AppError.NotFound

    else -> AppError.Unknown
}
