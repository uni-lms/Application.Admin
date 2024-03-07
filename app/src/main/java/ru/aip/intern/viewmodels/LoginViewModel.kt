package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.aip.intern.domain.auth.data.LoginRequest
import ru.aip.intern.storage.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val storage: DataStoreRepository) : ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _formEnabled = MutableLiveData(true)
    val formEnabled: LiveData<Boolean> = _formEnabled

    private val _isEmailWithError = MutableLiveData(false)
    val isEmailWithError: LiveData<Boolean> = _isEmailWithError

    private val _emailErrorMessage = MutableLiveData("")
    val emailErrorMessage: LiveData<String> = _emailErrorMessage

    private val _isPasswordWithError = MutableLiveData(false)
    val isPasswordWithError: LiveData<Boolean> = _isPasswordWithError

    private val _passwordErrorMessage = MutableLiveData("")
    val passwordErrorMessage: LiveData<String> = _passwordErrorMessage

    private val _askedForNotificationPermission = MutableLiveData(false)
    val askedForNotificationPermission: LiveData<Boolean> = _askedForNotificationPermission

    init {
        viewModelScope.launch {
            storage.askedForNotificationPermission.collect {
                _askedForNotificationPermission.value = it
            }
        }
    }

    object FieldsValidationState {
        var email: Boolean = true
        var password: Boolean = true
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setAskedForNotificationPermission(value: Boolean) {
        _askedForNotificationPermission.value = value
        viewModelScope.launch {
            storage.saveAskedForNotificationPermissionStatus(value)
        }
    }

    private fun validateEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }

    private fun validatePassword(password: String): Boolean {
        return password.isNotBlank()
    }

    fun validate(): Boolean {

        if (!validateEmail(_email.value ?: "")) {
            _isEmailWithError.value = true
            _emailErrorMessage.value = "Некорректный формат электропочты"
            FieldsValidationState.email = false
        } else {
            FieldsValidationState.email = true
        }

        if (!validatePassword(_password.value ?: "")) {
            _isPasswordWithError.value = true
            _passwordErrorMessage.value = "Пароль не может быть пустым"
            FieldsValidationState.password = false
        } else {
            FieldsValidationState.password = true
        }

        if (FieldsValidationState.email) {
            _isEmailWithError.value = false
            _emailErrorMessage.value = ""
        }

        if (FieldsValidationState.password) {
            _isPasswordWithError.value = false
            _passwordErrorMessage.value = ""
        }
        return FieldsValidationState.email && FieldsValidationState.password
    }

    fun login() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _formEnabled.value = false

            delay(3500)

            val request = LoginRequest(email.value!!, password.value!!)
            val fcmToken = ""

            _isRefreshing.value = false
            _formEnabled.value = true
        }
    }

}