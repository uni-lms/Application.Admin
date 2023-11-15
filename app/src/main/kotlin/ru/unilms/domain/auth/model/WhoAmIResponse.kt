package ru.unilms.domain.auth.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.auth.util.enums.UserRole

@Serializable
data class WhoAmIResponse(val email: String, val role: UserRole, val fullName: String)
