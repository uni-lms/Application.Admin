package ru.unilms.domain.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class WhoAmIResponse(val email: String, val role: String)
