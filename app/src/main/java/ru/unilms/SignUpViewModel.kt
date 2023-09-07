package ru.unilms

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.di.ResourcesProvider
import ru.unilms.domain.model.auth.SignupRequest
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(resourcesProvider: ResourcesProvider) : ViewModel() {
    var form = SignUpForm(resourcesProvider)
    val pickedImage = mutableStateOf<Uri?>(null)

    private fun validate() {
        form.validate(true)
        Log.d("MainViewModel", "Validate (form is valid: ${form.isValid})")
    }

    fun submit() {
        validate()
        if (form.isValid) {
            val signupRequest = SignupRequest(
                firstName = form.firstName.state.value!!,
                lastName = form.lastName.state.value!!,
                patronymic = form.patronymic.state.value,
                dateOfBirth = form.dateOfBirth.state.value!!,
                email = form.email.state.value!!,
                avatar = "",
                gender = UUID.randomUUID(),
                role = UUID.randomUUID()
            )
            Log.d("SignupViewModel", pickedImage.value.toString())
            Log.d("SignupViewModel", signupRequest.toString())
        }
    }
}
