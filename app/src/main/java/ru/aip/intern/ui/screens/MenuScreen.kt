package ru.aip.intern.ui.screens

import androidx.compose.runtime.Composable
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.Greeting

@Composable
fun MenuScreen() {
    BaseScreen {
        Greeting(name = "menu")
    }
}