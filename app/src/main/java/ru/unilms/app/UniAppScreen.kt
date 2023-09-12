package ru.unilms.app

import ru.unilms.R

enum class UniAppScreen(val title: Int = R.string.app_name) {
    SelectApiUri(R.string.screen_server_select),
    LoginOrRegister,
    Login(R.string.login),
    SignUp(R.string.register),
    Feed(R.string.feed),
    Calendar(R.string.calendar),
}