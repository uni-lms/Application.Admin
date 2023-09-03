package ru.unilms.network.services

import retrofit2.http.Body
import retrofit2.http.POST
import ru.unilms.domain.model.auth.LoginRequest
import ru.unilms.domain.model.auth.LoginResponse

interface AuthService {
    @POST("/v1/auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse
}