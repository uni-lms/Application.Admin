package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.auth.data.WhoamiResponse
import ru.aip.intern.domain.auth.service.AuthService
import ru.aip.intern.domain.notifications.service.NotificationsService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.storage.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    val storage: DataStoreRepository,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val authService: AuthService,
    private val notificationsService: NotificationsService
) : ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    private val _unreadNotificationsCount = MutableLiveData(0)

    private val _whoamiData = MutableLiveData(WhoamiResponse(email = "", fullName = ""))

    val unreadNotificationsCount: LiveData<Int> = _unreadNotificationsCount
    val isRefreshing: LiveData<Boolean> = _isRefreshing
    val whoamiData: LiveData<WhoamiResponse> = _whoamiData

    init {

        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true

            val whoamiResponse = authService.whoami()

            if (whoamiResponse.isSuccess) {
                _whoamiData.value = whoamiResponse.value
            } else {
                snackbarMessageHandler.postMessage(whoamiResponse.errorMessage!!)
            }

            val notificationsResponse = notificationsService.getUnreadAmount()

            if (notificationsResponse.isSuccess) {
                _unreadNotificationsCount.value = notificationsResponse.value
            } else {
                snackbarMessageHandler.postMessage(notificationsResponse.errorMessage!!)
            }

            _isRefreshing.value = false
        }
    }

    fun logOut() {
        viewModelScope.launch {
            storage.saveApiKey("")
        }
    }

}