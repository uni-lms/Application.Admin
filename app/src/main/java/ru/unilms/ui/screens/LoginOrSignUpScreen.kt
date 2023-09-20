package ru.unilms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.viewmodels.UniAppViewModel

@Composable
fun LoginOrSignUpScreen(goToLoginScreen: () -> Unit, goToSignUpScreen: () -> Unit) {

    val viewModel = hiltViewModel<UniAppViewModel>()
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = {
            scope.launch {
                viewModel.store.updateToken("not-empty-value")
            }
        }) {
            Text(
                text = stringResource(R.string.screen_login),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Text(
            text = stringResource(id = R.string.service_or),
            style = MaterialTheme.typography.headlineSmall
        )
        TextButton(onClick = { goToSignUpScreen() }) {
            Text(
                text = stringResource(R.string.screen_registration),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}