package ru.ifedorov.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.ifedorov.network.di.KinopoiskApiKey
import javax.inject.Inject

private const val API_KEY_HEADER = "X-API-KEY"

class ApiKeyInterceptor @Inject constructor(
    @param:KinopoiskApiKey private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (apiKey.isBlank()) {
            return chain.proceed(request)
        }

        val requestWithApiKey = request.newBuilder()
            .addHeader(API_KEY_HEADER, apiKey)
            .build()

        return chain.proceed(requestWithApiKey)
    }
}
