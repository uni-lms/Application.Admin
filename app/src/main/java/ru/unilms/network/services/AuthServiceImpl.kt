package ru.unilms.network.services

import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import ru.unilms.domain.model.auth.LoginRequest
import ru.unilms.domain.model.auth.LoginResponse
import ru.unilms.domain.model.error.ErrorResponse

class AuthServiceImpl() : AuthService {
    override suspend fun login(body: LoginRequest): Response<LoginResponse, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Post
            setBody(body)
            url("${HttpClientFactory.baseUrl}/v1/auth/login")
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
        }
    }
}