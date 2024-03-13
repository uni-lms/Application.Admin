package ru.aip.intern.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.Greeting
import ru.aip.intern.viewmodels.InternshipViewModel
import java.util.UUID

@Composable
fun InternshipScreen(
    title: MutableState<String>,
    internshipId: UUID,
    goToScreen: (Screen, UUID) -> Unit
) {

    title.value = "Стажировка"

    val viewModel = hiltViewModel<InternshipViewModel, InternshipViewModel.Factory>(
        creationCallback = { factory -> factory.create(internshipId) }
    )

    BaseScreen {
        Greeting(name = "Стажировка")
    }

}