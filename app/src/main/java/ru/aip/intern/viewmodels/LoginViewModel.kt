package ru.aip.intern.viewmodels

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.domain.auth.data.LoginRequest
import ru.aip.intern.domain.auth.service.AuthService
import ru.aip.intern.permissions.PermissionManager
import ru.aip.intern.permissions.PermissionStatus
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.storage.DataStoreRepository
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.LoginState
import ru.aip.intern.util.UiText
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val storage: DataStoreRepository,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val authService: AuthService,
    private val titleManager: TitleManager,
    private val permissionManager: PermissionManager
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"

    init {
        viewModelScope.launch {

            titleManager.update(UiText.StringResource(R.string.login))

            storage.askedForNotificationPermission.collect { asked ->
                _state.update {
                    it.copy(
                        askedForNotificationPermission = asked
                    )
                }
            }
        }
    }

    private object FieldsValidationState {
        var email: Boolean = true
        var password: Boolean = true
    }

    fun setEmail(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    fun setPassword(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }

    private fun setAskedForNotificationPermission() {
        _state.update {
            it.copy(
                askedForNotificationPermission = true
            )
        }
        viewModelScope.launch {
            storage.saveAskedForNotificationPermissionStatus(true)
        }
    }

    fun havePermission() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            val notificationPermissionStatus = permissionManager.checkPermission(
                Manifest.permission.POST_NOTIFICATIONS
            )

            _state.update {
                it.copy(
                    notificationPermissionStatus = notificationPermissionStatus,
                    isNotificationPermissionDialogShown = notificationPermissionStatus == PermissionStatus.DENIED && !it.askedForNotificationPermission
                )
            }


            setAskedForNotificationPermission()
        }
    }

    fun askPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            hideDialog()

            permissionManager.requestPermission(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    fun hideDialog() {
        _state.update {
            it.copy(
                isNotificationPermissionDialogShown = false
            )
        }
    }

    private fun validateEmail(email: String) {
        if (email.matches(_emailRegex.toRegex())) {
            _state.update {
                it.copy(
                    emailError = UiText.StringResource(R.string.empty)
                )
            }
            FieldsValidationState.email = true
        } else {
            _state.update {
                it.copy(
                    emailError = UiText.StringResource(R.string.invalid_email_format)
                )
            }
            FieldsValidationState.email = false
        }
    }

    private fun validatePassword(password: String) {
        if (password.isNotBlank()) {
            _state.update {
                it.copy(
                    passwordError = UiText.StringResource(R.string.empty)
                )
            }
            FieldsValidationState.password = true
        } else {
            _state.update {
                it.copy(
                    passwordError = UiText.StringResource(R.string.password_must_not_be_empty)
                )
            }
            FieldsValidationState.password = false
        }
    }

    fun validate(): Boolean {

        validateEmail(_state.value.email)

        validatePassword(_state.value.password)

        return FieldsValidationState.email && FieldsValidationState.password
    }

    fun login(redirect: () -> Unit) {

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true,
                    isFormEnabled = false
                )
            }

            val request = LoginRequest(_state.value.email, _state.value.password)
            var fcmToken = ""

            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener

                }

                val token = task.result
                Log.d("FCM", token)

                fcmToken = token
            }

            val response = authService.logIn(request, fcmToken)

            if (response.isSuccess) {
                storage.saveApiKey(response.value!!.accessToken)
                redirect()
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = false,
                    isFormEnabled = true
                )
            }

        }

    }

}