package ru.unilms.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.screens.SelectApiUriScreen
import ru.unilms.screens.SignUpScreen
import ru.unilms.viewmodels.UniAppViewModel

@Composable
fun UniApp() {
    val viewModel = hiltViewModel<UniAppViewModel>()

    val token = viewModel.token?.collectAsState(initial = "")?.value!!
    val apiUri = viewModel.apiUri?.collectAsState(initial = "")?.value!!

    apiUri.ifEmpty {
        return SelectApiUriScreen()
    }

    token.ifEmpty {
        return SignUpScreen()
    }
}