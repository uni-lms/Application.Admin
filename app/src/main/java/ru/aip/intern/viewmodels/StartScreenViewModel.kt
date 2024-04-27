package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.auth.AuthManager
import ru.aip.intern.domain.auth.service.AuthService
import ru.aip.intern.navigation.Screen
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.storage.DataStoreRepository
import ru.aip.intern.ui.state.StartScreenState
import ru.aip.intern.util.UiText
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val authService: AuthService,
    private val authManager: AuthManager,
    private val snackbarMessageHandler: SnackbarMessageHandler,
) :
    ViewModel() {

    private val _state = MutableStateFlow(StartScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    showSplashScreen = true
                )
            }


            val response = authService.whoami()

            val determinedScreen = when {
                response.isSuccess -> Screen.Internships
                response.errorMessage?.contains("expired") == true -> {
                    snackbarMessageHandler.postMessage(
                        UiText.StringResource(R.string.token_has_expired)
                    )
                    Screen.Login
                }

                else -> Screen.Login
            }

            _state.update {
                it.copy(
                    startScreen = determinedScreen,
                    showSplashScreen = false
                )
            }
        }
    }

    fun updateStartScreen(value: Screen) {
        _state.update {
            it.copy(
                startScreen = value
            )
        }
    }
}