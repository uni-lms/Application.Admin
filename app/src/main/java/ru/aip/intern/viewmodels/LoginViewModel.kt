package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

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

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    private fun validateEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }

    private fun validatePassword(password: String): Boolean {
        return password.isNotBlank()
    }

    fun validate(): Boolean {

        var isValid = true

        if (!validateEmail(_email.value ?: "")) {
            _isEmailWithError.value = true
            _emailErrorMessage.value = "Некорректный формат электропочты"
            isValid = false
        }

        if (!validatePassword(_password.value ?: "")) {
            _isPasswordWithError.value = true
            _passwordErrorMessage.value = "Пароль не может быть пустым"
            isValid = false
        }

        if (isValid) {
            _isEmailWithError.value = false
            _emailErrorMessage.value = ""

            _isPasswordWithError.value = false
            _passwordErrorMessage.value = ""
        }
        return isValid
    }

    fun login() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _formEnabled.value = false

            delay(3500)

            _isRefreshing.value = false
            _formEnabled.value = true
        }
    }

}