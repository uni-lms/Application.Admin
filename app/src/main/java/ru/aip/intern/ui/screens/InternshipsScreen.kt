package ru.aip.intern.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.Greeting
import ru.aip.intern.viewmodels.InternshipsViewModel

@Composable
fun InternshipsScreen() {
    val viewModel: InternshipsViewModel = hiltViewModel()

    viewModel.dummyApiKey()

    BaseScreen {
        Greeting(name = "internships")
    }
}