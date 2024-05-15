package ru.aip.intern.domain.internal.service

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import ru.aip.intern.domain.internal.data.ReleaseInfo
import ru.aip.intern.networking.HttpClientFactory
import ru.aip.intern.networking.Response
import ru.aip.intern.networking.safeDownload
import ru.aip.intern.networking.safeRequest
import javax.inject.Inject

class InternalService @Inject constructor() {
    suspend fun getLatestRelease(version: String): Response<ReleaseInfo> {
        val httpClient = HttpClientFactory.httpClient
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/internal/app-version?version=${version}")
            accept(ContentType.Application.Json)
        }
    }

    suspend fun downloadFile(
        url: String,
        progressListener: (Float) -> Unit,
        configureRequest: HttpRequestBuilder.() -> Unit = {}
    ): Response<ByteArray> {
        val httpClient = HttpClientFactory.httpClient
        return httpClient.safeDownload(url, progressListener, configureRequest)
    }
}