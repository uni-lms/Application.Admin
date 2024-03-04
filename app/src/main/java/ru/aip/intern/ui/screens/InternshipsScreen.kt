package ru.aip.intern.ui.screens

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.Greeting
import ru.aip.intern.viewmodels.InternshipsViewModel

@Composable
fun InternshipsScreen(title: MutableState<String>) {
    val viewModel: InternshipsViewModel = hiltViewModel()
    title.value = "Стажировки"
    viewModel.dummyApiKey()

    BaseScreen {
        Greeting(name = "internships")
    }
}