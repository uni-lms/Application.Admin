package ru.unilms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import ru.unilms.R

@Composable
fun LoginOrSignUpScreen(goToLoginScreen: () -> Unit, goToSignUpScreen: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = goToLoginScreen) {
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