package ru.unilms.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.model.auth.LoginRequest
import ru.unilms.network.services.AuthService
import ru.unilms.network.services.AuthServiceImpl
import ru.unilms.ui.forms.LoginForm
import ru.unilms.utils.networking.processResponse
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    private var store: DataStore? = null
    private var baseUrl = ""

    var form = LoginForm()

    init {
        store = DataStore(context)
        viewModelScope.launch {
            store!!.apiUri.collect {
                baseUrl = it
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
                AuthService.client.use { client ->
                    val service = AuthServiceImpl(client, baseUrl)
                    processResponse(
                        service.login(loginRequest),
                        onSuccess = {
                            viewModelScope.launch {
                                store?.updateToken(it.token)
                                goToFeedScreen()
                            }
                        },
                        onError = {
                            val message =
                                if (it != null) "Произошла ошибка: ${it.reason}"
                                else "Произошла неизвестная ошибка"
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }

}