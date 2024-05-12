package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.domain.content.quiz.service.QuizService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.QuestionState
import ru.aip.intern.util.UiText
import java.util.UUID

@HiltViewModel(assistedFactory = QuestionViewModel.Factory::class)
class QuestionViewModel @AssistedInject constructor(
    private val titleManager: TitleManager,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val quizService: QuizService,
    @Assisted private val attemptId: UUID,
    @Assisted private val question: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(attemptId: UUID, question: Int): QuestionViewModel
    }

    private val _state = MutableStateFlow(QuestionState())
    val state = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {

            titleManager.update(UiText.StringResource(R.string.question_title, question))

            _state.update {
                it.copy(
                    isRefreshing = true,
                    question = question
                )
            }

            val response = quizService.getQuestion(attemptId, question)

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        questionInfo = response.value!!
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