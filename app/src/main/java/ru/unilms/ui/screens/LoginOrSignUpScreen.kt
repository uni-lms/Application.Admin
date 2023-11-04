package ru.unilms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import ru.unilms.R
import ru.unilms.app.UniAppScreen
import ru.unilms.data.AppBarState
import java.util.UUID

@Composable
fun LoginOrSignUpScreen(
    navigate: (UniAppScreen, UUID?) -> Unit,
    onComposing: (AppBarState) -> Unit
) {


    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = { navigate(UniAppScreen.Login, null) }) {
            Text(
                text = stringResource(R.string.screen_login),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Text(
            text = stringResource(id = R.string.service_or),
            style = MaterialTheme.typography.headlineSmall
        )
        TextButton(onClick = { navigate(UniAppScreen.SignUp, null) }) {
            Text(
                text = stringResource(R.string.screen_registration),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}