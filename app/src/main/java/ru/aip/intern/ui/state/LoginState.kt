package ru.aip.intern.ui.state

import ru.aip.intern.R
import ru.aip.intern.util.UiText

data class LoginState(
    val isRefreshing: Boolean = false,
    val isFormEnabled: Boolean = true,
    val email: String = "",
    val password: String = "",
    val emailError: UiText = UiText.StringResource(R.string.empty),
    val passwordError: UiText = UiText.StringResource(R.string.empty),
    val askedForNotificationPermission: Boolean = false,
)
