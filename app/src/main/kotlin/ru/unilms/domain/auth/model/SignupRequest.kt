package ru.unilms.domain.auth.model

import java.util.Date
import java.util.UUID

data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val patronymic: String?,
    val dateOfBirth: Date,
    val email: String,
    val role: UUID,
    val gender: UUID,
    val avatar: String,
)
