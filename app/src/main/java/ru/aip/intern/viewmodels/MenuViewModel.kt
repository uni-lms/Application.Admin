package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.auth.data.WhoamiResponse
import ru.aip.intern.domain.auth.service.AuthService
import ru.aip.intern.storage.DataStoreRepository
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MenuViewModel @Inject constructor(val storage: DataStoreRepository) : ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    private val _unreadNotificationsCount = MutableLiveData(0)

    private val _whoamiData = MutableLiveData(WhoamiResponse(email = "", fullName = ""))

    val unreadNotificationsCount: LiveData<Int> = _unreadNotificationsCount
    val isRefreshing: LiveData<Boolean> = _isRefreshing
    val whoamiData: LiveData<WhoamiResponse> = _whoamiData

    private lateinit var service: AuthService

    init {

        viewModelScope.launch {
            storage.apiKey.collect {
                service = AuthService(it ?: "")
            }
        }

        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true

            val response = service.whoami()

            if (response.isSuccess) {
                _whoamiData.value = response.value
            }

            _unreadNotificationsCount.value = Random.nextInt(0, 3)
            _isRefreshing.value = false
        }
    }

    fun logOut() {
        viewModelScope.launch {
            storage.saveApiKey("")
        }
    }

}