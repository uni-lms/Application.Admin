package ru.unilms.domain.app.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class UniAppViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    val store = DataStore(context)

    var token: Flow<String>? = null
    var apiUri: Flow<String>? = null

    init {
        viewModelScope.launch {
            token = store.token
            apiUri = store.apiUri
        }
    }
}