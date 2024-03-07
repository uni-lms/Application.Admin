package ru.aip.intern.domain.auth.service

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import ru.aip.intern.domain.auth.data.LoginRequest
import ru.aip.intern.domain.auth.data.LoginResponse
import ru.aip.intern.networking.HttpClientFactory
import ru.aip.intern.networking.Response
import ru.aip.intern.networking.safeRequest

class AuthService(private val token: String) {
    suspend fun logIn(request: LoginRequest, fcmToken: String): Response<LoginResponse> {
        val httpClient = HttpClientFactory.httpClient
        return httpClient.safeRequest {
            method = HttpMethod.Post
            url("/auth/log-in")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            headers {
                append("x-FCM-Token", fcmToken)
            }
        }
    }
}