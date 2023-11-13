package ru.unilms.domain.auth.view.screen

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
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import java.util.UUID

@Composable
fun LoginOrSignUpScreen(
    navigate: (Screens, UUID?) -> Unit,
    onComposing: (AppBarState, FabState) -> Unit,
) {


    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            ),
            FabState(
                fab = {}
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = { navigate(Screens.Login, null) }) {
            Text(
                text = stringResource(R.string.screen_login),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Text(
            text = stringResource(id = R.string.service_or),
            style = MaterialTheme.typography.headlineSmall
        )
        TextButton(onClick = { navigate(Screens.SignUp, null) }) {
            Text(
                text = stringResource(R.string.screen_registration),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}