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
import ru.unilms.network.services.Response
import ru.unilms.ui.forms.LoginForm
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    private var store: DataStore? = null
    private var baseUrl = ""

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

                    when (val res = service.login(loginRequest)) {
                        is Response.Success -> {
                            store?.updateToken(res.body.token)
                            goToFeedScreen()
                        }

                        is Response.Error.HttpError -> {
                            Toast.makeText(
                                context,
                                "Произошла ошибка: ${res.errorBody?.reason}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Response.Error.NetworkError -> {
                            Toast.makeText(
                                context,
                                "Произошла ошибка: Нет доступа к интернету",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Response.Error.SerializationError -> {
                            Toast.makeText(
                                context,
                                "Произошла ошибка: Ошибка разбора ответа",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
    }

    var form = LoginForm()

}