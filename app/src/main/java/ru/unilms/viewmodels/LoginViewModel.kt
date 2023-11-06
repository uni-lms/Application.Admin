package ru.unilms.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.model.auth.LoginRequest
import ru.unilms.network.HttpClientFactory
import ru.unilms.network.services.auth.AuthServiceImpl
import ru.unilms.ui.forms.LoginForm
import ru.unilms.utils.networking.processResponse
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    private var store: DataStore = DataStore(context)
    private lateinit var baseUrl: String
    private val service = AuthServiceImpl()

    var form = LoginForm()

    init {
        viewModelScope.launch {
            store.apiUri.collect {
                HttpClientFactory.baseUrl = it
            }
        }
    }

    private fun validate() {
        form.validate(true)
        Log.d("MainViewModel", "Validate (form is valid: ${form.isValid})")
    }

    fun submit(goToFeedScreen: () -> Unit) {
        validate()
        if (form.isValid) {
            val loginRequest = LoginRequest(
                email = form.email.state.value ?: "",
                password = form.password.state.value ?: "",
            )
            viewModelScope.launch {
                val result = processResponse(service.login(loginRequest))
                if (result != null) {
                    store.updateToken(result.token)
                    goToFeedScreen()
                }
            }
        }
    }

}