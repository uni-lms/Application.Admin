package ru.aip.intern.snackbar

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.aip.intern.util.UiText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarMessageHandler @Inject constructor(@ApplicationContext private val context: Context) {

    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    suspend fun postMessage(message: String) {
        _message.emit(message)
    }

    suspend fun postMessage(message: UiText) {
        _message.emit(message.asString(context))
    }

}