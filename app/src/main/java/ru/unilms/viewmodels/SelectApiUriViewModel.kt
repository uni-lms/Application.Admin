package ru.unilms.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.forms.SelectApiUriForm
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SelectApiUriViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    val form = SelectApiUriForm()
    private val store = DataStore(context)

    private fun validate() {
        form.validate(true)
        Log.d("MainViewModel", "Validate (form is valid: ${form.isValid})")
    }

    fun submit() {
        validate()
        if (form.isValid) {
            viewModelScope.launch {
                store.updateApiUri(form.apiUri.state.value!!)
            }
        }
    }
}