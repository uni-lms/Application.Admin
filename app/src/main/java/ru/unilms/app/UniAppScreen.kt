package ru.unilms.app

import ru.unilms.R

enum class UniAppScreen(
    val title: Int = R.string.app_name,
    val canGoBack: Boolean = true,
    val showBottomAppBar: Boolean = false
) {
    SelectApiUri(R.string.screen_server_select, false),
    LoginOrRegister,
    Login(R.string.login),
    SignUp(R.string.register),
    Feed(R.string.feed, false, true),
    Calendar(R.string.calendar, false, true),
}