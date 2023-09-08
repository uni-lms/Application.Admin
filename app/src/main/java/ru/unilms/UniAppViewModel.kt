package ru.unilms

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.unilms.data.PreferencesKeys
import ru.unilms.extensions.authDataStore
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class UniAppViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    var token = ""
    var apiUri = ""

    init {
        viewModelScope.launch {
            context.authDataStore.data.map { preferences ->
                preferences[PreferencesKeys.Token] ?: ""
            }.collect {
                token = it
            }
            context.authDataStore.data.map { preferences ->
                preferences[PreferencesKeys.ApiUri] ?: ""
            }.collect {
                apiUri = it
            }
        }
    }
}