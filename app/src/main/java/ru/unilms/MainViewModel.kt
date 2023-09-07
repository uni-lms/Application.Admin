package ru.unilms

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.di.ResourcesProvider
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(resourcesProvider: ResourcesProvider) : ViewModel() {
    var form = SignUpForm(resourcesProvider)

    fun validate() {
        form.validate(true)
        Log.d("MainViewModel", "Validate (form is valid: ${form.isValid})")
    }
}
