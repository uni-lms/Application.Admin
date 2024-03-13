package ru.aip.intern.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.Greeting
import java.util.UUID

@Composable
fun InternshipScreen(
    title: MutableState<String>,
    internshipId: UUID,
    goToScreen: (Screen, UUID) -> Unit
) {

    title.value = "Стажировка"

    BaseScreen {
        Greeting(name = "Стажировка")
    }

}