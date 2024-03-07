package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(title: MutableState<String>) {

    title.value = "Вход в аккаунт"

    val viewModel: LoginViewModel = hiltViewModel()

    fun submit() {
        viewModel.login()
    }

    BaseScreen {

        val email = viewModel.email.observeAsState("")
        val password = viewModel.password.observeAsState("")
        val formEnabledState = viewModel.formEnabled.observeAsState(true)

        val refreshing = viewModel.isRefreshing.observeAsState(false)
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing.value,
            onRefresh = { }
        )

        Box {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { viewModel.setEmail(it) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    enabled = formEnabledState.value,
                    label = {
                        Text(
                            text = "Электропочта"
                        )
                    }
                )

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { viewModel.setPassword(it) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            submit()
                        }
                    ),
                    enabled = formEnabledState.value,
                    label = {
                        Text(
                            text = "Пароль"
                        )
                    }
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        submit()
                    },
                    enabled = formEnabledState.value
                ) {
                    Text(text = "Войти")
                }


            }

            PullRefreshIndicator(
                refreshing.value,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}