package ru.unilms.domain.manage.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.processResponse
import ru.unilms.domain.manage.model.User
import ru.unilms.domain.manage.network.ManageServiceImpl
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {
    private var store: DataStore = DataStore(context)
    private lateinit var service: ManageServiceImpl

    init {
        viewModelScope.launch {
            store.apiUri.collect {
                HttpClientFactory.baseUrl = it
            }
        }

        viewModelScope.launch {
            store.token.collect {
                service = ManageServiceImpl(it)
            }
        }
    }

    suspend fun getUsers(): List<User>? {
        var result: List<User>? = null

        val response = service.getUsers()

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result
    }
}