package ru.unilms.domain.auth.network

import ru.unilms.domain.auth.model.LoginRequest
import ru.unilms.domain.auth.model.LoginResponse
import ru.unilms.domain.auth.model.WhoAmIResponse
import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.Response

interface AuthService {
    suspend fun login(body: LoginRequest): Response<LoginResponse, ErrorResponse>
    suspend fun whoami(): Response<WhoAmIResponse, ErrorResponse>
}