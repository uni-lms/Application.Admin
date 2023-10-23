package ru.unilms.network.services

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import ru.unilms.domain.model.auth.LoginRequest
import ru.unilms.domain.model.auth.LoginResponse
import ru.unilms.domain.model.error.ErrorResponse

class AuthServiceImpl(private val client: HttpClient, private val baseUrl: String) : AuthService {
    override suspend fun login(body: LoginRequest): Response<LoginResponse, ErrorResponse> {
        return client.safeRequest {
            method = HttpMethod.Post
            url("$baseUrl/auth/login")
        }
    }
}