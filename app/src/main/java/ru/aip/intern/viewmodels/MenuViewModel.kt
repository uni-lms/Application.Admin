package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.domain.auth.service.AuthService
import ru.aip.intern.domain.notifications.service.NotificationsService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.storage.DataStoreRepository
import ru.aip.intern.ui.state.MenuState
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    val storage: DataStoreRepository,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val authService: AuthService,
    private val notificationsService: NotificationsService
) : ViewModel() {

    private val _state = MutableStateFlow(MenuState())
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

            val whoamiResponse = authService.whoami()

            if (whoamiResponse.isSuccess) {
                _state.update {
                    it.copy(
                        whoAmIData = whoamiResponse.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(whoamiResponse.errorMessage!!)
            }

            val notificationsResponse = notificationsService.getUnreadAmount()

            if (notificationsResponse.isSuccess) {
                _state.update {
                    it.copy(
                        notificationsCount = notificationsResponse.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(notificationsResponse.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            storage.saveApiKey("")
        }
    }

}