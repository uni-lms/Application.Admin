package ru.aip.intern.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.notifications.data.NotificationsList
import ru.aip.intern.domain.notifications.service.NotificationsService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val notificationsService: NotificationsService
) : ViewModel() {

    var isRefreshing = MutableLiveData(false)
        private set

    val defaultContent = NotificationsList(emptyList())

    var data = MutableLiveData(defaultContent)
        private set

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            isRefreshing.value = true
            val response = notificationsService.getNotifications()

            if (response.isSuccess) {
                data.value = response.value!!
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            isRefreshing.value = false
        }
    }

}