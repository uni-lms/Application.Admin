package ru.unilms.network.services.auth

import ru.unilms.domain.model.auth.LoginRequest
import ru.unilms.domain.model.auth.LoginResponse
import ru.unilms.domain.model.auth.WhoAmIResponse
import ru.unilms.domain.model.error.ErrorResponse
import ru.unilms.network.Response

interface AuthService {
    suspend fun login(body: LoginRequest): Response<LoginResponse, ErrorResponse>
    suspend fun whoami(): Response<WhoAmIResponse, ErrorResponse>
}