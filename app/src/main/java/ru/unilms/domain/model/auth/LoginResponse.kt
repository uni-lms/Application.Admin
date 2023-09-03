package ru.unilms.domain.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    val email: String,
    val token: String
) : Parcelable
