package ru.unilms

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.unilms.data.PreferencesKeys
import ru.unilms.extensions.authDataStore
import javax.inject.Inject

@HiltViewModel
class SelectApiUriViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    val form = SelectApiUriForm()

    private fun validate() {
        form.validate(true)
        Log.d("MainViewModel", "Validate (form is valid: ${form.isValid})")
    }

    fun submit() {
        validate()
        if (form.isValid) {
            viewModelScope.launch {
                context.authDataStore.edit { preferences ->
                    preferences[PreferencesKeys.ApiUri] = form.apiUri.state.value!!
                }
            }
        }
    }
}