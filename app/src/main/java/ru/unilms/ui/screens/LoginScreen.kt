package ru.unilms.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.R
import ru.unilms.ui.components.form.M3TextField
import ru.unilms.viewmodels.LoginViewModel

@Composable
fun LoginScreen(goToFeedScreen: () -> Unit) {
    val viewModel = hiltViewModel<LoginViewModel>()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            M3TextField(
                label = stringResource(id = R.string.field_email),
                form = viewModel.form,
                fieldState = viewModel.form.email,
                keyboardType = KeyboardType.Email,
                keyboardCapitalization = KeyboardCapitalization.None,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3TextField(
                label = stringResource(id = R.string.field_password),
                form = viewModel.form,
                fieldState = viewModel.form.password,
                keyboardType = KeyboardType.Password,
                keyboardCapitalization = KeyboardCapitalization.None,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()

            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = stringResource(R.string.button_forgot_password))
                    }
                    Button(onClick = { viewModel.submit { goToFeedScreen() } }) {
                        Text(text = stringResource(R.string.button_login))
                    }
                }
            }
        }


    }
}