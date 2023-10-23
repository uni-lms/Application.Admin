package ru.unilms.network.services

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import ru.unilms.domain.model.auth.LoginRequest
import ru.unilms.domain.model.auth.LoginResponse
import ru.unilms.domain.model.error.ErrorResponse

interface AuthService {
    suspend fun login(body: LoginRequest): Response<LoginResponse, ErrorResponse>

    companion object {
        val client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json()
            }
        }
    }
}