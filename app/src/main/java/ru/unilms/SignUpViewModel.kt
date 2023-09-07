package ru.unilms

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.di.ResourcesProvider
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(resourcesProvider: ResourcesProvider) : ViewModel() {
    var form = SignUpForm(resourcesProvider)
    val pickedImage = mutableStateOf<Uri?>(null)

    fun validate() {
        form.validate(true)
        Log.d("MainViewModel", "Validate (form is valid: ${form.isValid})")
    }
}
