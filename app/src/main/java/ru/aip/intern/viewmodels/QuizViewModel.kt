package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.aip.intern.domain.content.quiz.QuizInfo
import ru.aip.intern.snackbar.SnackbarMessageHandler
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@HiltViewModel(assistedFactory = QuizViewModel.Factory::class)
class QuizViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    @Assisted private val id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): QuizViewModel
    }

    var isRefreshing = MutableStateFlow(false)
        private set

    var data = MutableStateFlow(
        QuizInfo(
            UUID.randomUUID(),
            title = "",
            null,
            allowedAttempts = 1,
            availableUntil = LocalDateTime.now(),
            timeLimit = Duration.ZERO
        )
    )
        private set

    init {
        refresh(id)
    }

    fun refresh(id: UUID) {
        viewModelScope.launch {

        }
    }

}