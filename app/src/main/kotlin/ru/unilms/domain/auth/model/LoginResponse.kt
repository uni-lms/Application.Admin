package ru.unilms.domain.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val email: String,
    val token: String,
)
