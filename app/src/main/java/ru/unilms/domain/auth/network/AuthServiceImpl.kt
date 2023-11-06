package ru.unilms.domain.auth.network

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import ru.unilms.domain.auth.model.LoginRequest
import ru.unilms.domain.auth.model.LoginResponse
import ru.unilms.domain.auth.model.WhoAmIResponse
import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.common.network.safeRequest

class AuthServiceImpl(val token: String = "") : AuthService {
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

    override suspend fun whoami(): Response<WhoAmIResponse, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/auth/whoami")
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}