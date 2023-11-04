package ru.unilms.domain.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val email: String,
    val token: String
)
