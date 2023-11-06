package ru.unilms.domain.auth.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.auth.viewmodel.SelectApiUriViewModel
import ru.unilms.domain.common.view.component.field.M3TextField
import java.util.UUID

@Composable
fun SelectApiUriScreen(
    navigate: (Screens, UUID?) -> Unit,
    onComposing: (AppBarState) -> Unit
) {

    val viewModel = hiltViewModel<SelectApiUriViewModel>()

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            )
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            M3TextField(
                label = stringResource(id = R.string.field_api_uri),
                form = viewModel.form,
                fieldState = viewModel.form.apiUri,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(onClick = {
                    viewModel.submit {
                        navigate(
                            Screens.LoginOrRegister,
                            null
                        )
                    }
                }) {
                    Text(text = stringResource(R.string.button_save))
                }
            }
        }
    }
}
