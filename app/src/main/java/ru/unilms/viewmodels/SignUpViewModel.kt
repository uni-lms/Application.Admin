package ru.unilms.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.DataStore
import ru.unilms.di.ResourcesProvider
import ru.unilms.domain.model.auth.SignupRequest
import ru.unilms.domain.model.user.Gender
import ru.unilms.domain.model.user.Role
import ru.unilms.forms.SignUpForm
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    @ApplicationContext context: Context
) :
    ViewModel() {
    var form = SignUpForm(getGenders(), getRoles())
    val pickedImage = mutableStateOf<Uri?>(null)

    private val store = DataStore(context)

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

    fun submit(goToFeedScreen: () -> Unit) {
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
            viewModelScope.launch {
                // TODO: put here value from api response
                store.updateToken("some-not-empty-value")
            }
            goToFeedScreen()
        }
    }
}
