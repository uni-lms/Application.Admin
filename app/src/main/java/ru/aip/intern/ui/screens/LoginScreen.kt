package ru.aip.intern.ui.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.navigation.Screen
import ru.aip.intern.permissions.NotificationsPermissionsTextProvider
import ru.aip.intern.permissions.PermissionDialog
import ru.aip.intern.permissions.PermissionStatus
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.viewmodels.LoginViewModel
import ru.aip.intern.viewmodels.PermissionManagerViewModel
import ru.aip.intern.viewmodels.StartScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    title: MutableState<String>,
    navigateTo: (Screen) -> Unit
) {

    title.value = stringResource(R.string.login)

    val viewModel: LoginViewModel = hiltViewModel()
    val permissionsViewModel: PermissionManagerViewModel = hiltViewModel()
    val startScreenViewModel: StartScreenViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { }
    )

    val context = LocalContext.current

    var notificationPermissionStatus by remember {
        mutableStateOf(PermissionStatus.NOT_REQUESTED)
    }
    var notificationPermissionDialogStatus by remember {
        mutableStateOf(false)
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    fun submit() {
        if (viewModel.validate()) {
            viewModel.login { navigateTo(Screen.Internships) }
            startScreenViewModel.updateStartScreen(Screen.Internships)
        }
    }

    BaseScreen {


        LaunchedEffect(key1 = true) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermissionStatus = permissionsViewModel.checkPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                )

                notificationPermissionDialogStatus =
                    notificationPermissionStatus == PermissionStatus.DENIED && !state.askedForNotificationPermission

                viewModel.setAskedForNotificationPermission(true)
            }
        }


        Box {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.setEmail(it) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    enabled = state.isFormEnabled,
                    isError = state.emailError.asString().isNotBlank(),
                    supportingText = {
                        if (state.emailError.asString().isNotBlank()) {
                            Text(text = state.emailError.asString())
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.email)
                        )
                    }
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = { viewModel.setPassword(it) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (isPasswordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            submit()
                        }
                    ),
                    isError = state.passwordError.asString().isNotBlank(),
                    supportingText = {
                        if (state.passwordError.asString().isNotBlank()) {
                            Text(text = state.passwordError.asString())
                        }
                    },
                    enabled = state.isFormEnabled,
                    label = {
                        Text(
                            text = stringResource(R.string.password)
                        )
                    },
                    trailingIcon = {
                        val image = if (isPasswordVisible)
                            Icons.Outlined.Visibility
                        else Icons.Outlined.VisibilityOff

                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(imageVector = image, null)
                        }

                    }
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        submit()
                    },
                    enabled = state.isFormEnabled
                ) {
                    Text(text = stringResource(R.string.log_in))
                }


            }

            PullRefreshIndicator(
                state.isRefreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (notificationPermissionDialogStatus) {
                    PermissionDialog(
                        NotificationsPermissionsTextProvider(),
                        onDismiss = { },
                        onConfirmation = {
                            notificationPermissionDialogStatus = false
                            permissionsViewModel.requestPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                        },
                        onCancel = {
                            notificationPermissionDialogStatus = false
                        }
                    )
                }
            }
        }
    }
}