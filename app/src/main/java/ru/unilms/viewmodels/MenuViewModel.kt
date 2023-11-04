package ru.unilms.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.model.auth.WhoAmIResponse
import ru.unilms.network.services.AuthServiceImpl
import ru.unilms.network.services.HttpClientFactory
import ru.unilms.utils.enums.UserRole
import ru.unilms.utils.networking.processResponse
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MenuViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {

    private val store = DataStore(context)
    private lateinit var service: AuthServiceImpl
    val emptyUser = WhoAmIResponse("email@example.com", UserRole.Student, "Вася Пупкин")

    init {
        viewModelScope.launch {
            store.apiUri.collect {
                HttpClientFactory.baseUrl = it
            }
        }

        viewModelScope.launch {
            store.token.collect {
                service = AuthServiceImpl(it)
            }
        }
    }

    suspend fun whoami(): WhoAmIResponse {
        var result: WhoAmIResponse? = null
        val response = service.whoami()
        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        Log.d("whoami", result.toString())

        return result ?: emptyUser
    }

    fun logout() {
        viewModelScope.launch {
            store.updateToken("")
        }
    }
}