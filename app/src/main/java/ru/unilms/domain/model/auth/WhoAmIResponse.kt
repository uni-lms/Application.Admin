package ru.unilms.domain.model.auth

import kotlinx.serialization.Serializable
import ru.unilms.utils.enums.UserRole

@Serializable
data class WhoAmIResponse(val email: String, val role: UserRole, val fullName: String)
