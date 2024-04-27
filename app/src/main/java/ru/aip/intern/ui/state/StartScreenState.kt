package ru.aip.intern.ui.state

import ru.aip.intern.navigation.Screen

data class StartScreenState(
    val showSplashScreen: Boolean = true,
    val startScreen: Screen = Screen.Login
)