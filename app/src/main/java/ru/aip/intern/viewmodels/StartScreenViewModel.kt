package ru.aip.intern.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.domain.auth.service.AuthService
import ru.aip.intern.navigation.Screen
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.state.StartScreenState
import ru.aip.intern.util.UiText
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val authService: AuthService,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    @ApplicationContext private val context: Context
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
                response.errorMessage?.asString(context)?.contains("expired") == true -> {
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