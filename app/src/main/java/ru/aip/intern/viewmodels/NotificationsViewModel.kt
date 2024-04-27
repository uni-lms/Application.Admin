package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.domain.notifications.service.NotificationsService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.state.NotificationsState
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val notificationsService: NotificationsService
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationsState())
    val state = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }
            val response = notificationsService.getNotifications()

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        notificationsData = response.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

}