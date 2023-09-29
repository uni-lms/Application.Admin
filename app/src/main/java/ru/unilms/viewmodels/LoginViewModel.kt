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
import ru.unilms.ui.forms.LoginForm
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {

    private val store = DataStore(context)

    private fun validate() {
        form.validate(true)
        Log.d("MainViewModel", "Validate (form is valid: ${form.isValid})")
    }

    fun submit(goToFeedScreen: () -> Unit) {
//        validate()
        if (form.isValid || true) {
            val signupRequest = LoginRequest(
                email = form.email.state.value ?: "",
                password = form.password.state.value ?: "",
            )
            viewModelScope.launch {
                // TODO: put here value from api response
                store.updateToken("some-not-empty-value")
            }
            goToFeedScreen()
        }
    }

    var form = LoginForm()

}