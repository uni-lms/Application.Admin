package ru.aip.intern.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import ru.aip.intern.ui.components.BaseScreen

@Composable
fun CalendarScreen(title: MutableState<String>) {
    title.value = "Календарь"

    BaseScreen {

    }
}