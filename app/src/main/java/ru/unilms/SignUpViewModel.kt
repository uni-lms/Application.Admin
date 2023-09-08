package ru.unilms

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.di.ResourcesProvider
import ru.unilms.domain.model.auth.SignupRequest
import ru.unilms.domain.model.user.Gender
import ru.unilms.domain.model.user.Role
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val resourcesProvider: ResourcesProvider) :
    ViewModel() {
    var form = SignUpForm(getGenders(), getRoles())
    val pickedImage = mutableStateOf<Uri?>(null)

    private fun validate() {
        form.validate(true)
        Log.d("MainViewModel", "Validate (form is valid: ${form.isValid})")
    }

    private fun getGenders(): MutableList<Gender?> {
        // TODO: When backend will be available put here fetching list of genders from it
        return mutableListOf(
            Gender(UUID.randomUUID(), resourcesProvider.getString(R.string.gender_male)),
            Gender(UUID.randomUUID(), resourcesProvider.getString(R.string.gender_female))
        )
    }

    private fun getRoles(): MutableList<Role?> {
        // TODO: When backend will be available put here fetching list of roles from it
        return mutableListOf(
            Role(UUID.randomUUID(), resourcesProvider.getString(R.string.role_tutor)),
            Role(UUID.randomUUID(), resourcesProvider.getString(R.string.role_admin))
        )
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
                gender = form.gender.state.value!!.id,
                role = form.role.state.value!!.id
            )
            Log.d("SignupViewModel", pickedImage.value.toString())
            Log.d("SignupViewModel", signupRequest.toString())
        }
    }
}
