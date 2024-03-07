package ru.aip.intern.domain.internships.service

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import ru.aip.intern.domain.internships.data.Internship
import ru.aip.intern.networking.ErrorResponse
import ru.aip.intern.networking.HttpClientFactory
import ru.aip.intern.networking.Response
import ru.aip.intern.networking.ResponseWrapper
import ru.aip.intern.networking.safeRequest

class InternshipsService(private val token: String) {
    suspend fun getEnrolled(): Response<ResponseWrapper<List<Internship>>, ErrorResponse> {
        val httpClient = HttpClientFactory.httpClient
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/internships/enrolled")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    suspend fun getOwned(): Response<List<Internship>, ErrorResponse> {
        val httpClient = HttpClientFactory.httpClient
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/internships/owned")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}