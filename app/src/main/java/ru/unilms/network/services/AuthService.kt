package ru.unilms.network.services

import ru.unilms.domain.model.auth.LoginRequest
import ru.unilms.domain.model.auth.LoginResponse
import ru.unilms.domain.model.error.ErrorResponse

interface AuthService {
    suspend fun login(body: LoginRequest): Response<LoginResponse, ErrorResponse>
}