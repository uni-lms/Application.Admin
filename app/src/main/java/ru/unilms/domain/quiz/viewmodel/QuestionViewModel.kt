package ru.unilms.domain.quiz.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.processResponse
import ru.unilms.domain.quiz.model.AttemptInfo
import ru.unilms.domain.quiz.model.ChosenAnswer
import ru.unilms.domain.quiz.model.QuestionInfo
import ru.unilms.domain.quiz.network.QuizServiceImpl
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    private var store: DataStore = DataStore(context)
    private lateinit var service: QuizServiceImpl

    init {
        viewModelScope.launch {
            store.apiUri.collect {
                HttpClientFactory.baseUrl = it
            }
        }

        viewModelScope.launch {
            store.token.collect {
                service = QuizServiceImpl(it)
            }
        }
    }

    val emptyQuestion = QuestionInfo(
        UUID.randomUUID(),
        "",
        "",
        1,
        0,
        false,
        listOf(),
        listOf()
    )

    suspend fun getQuestionInfo(attemptId: UUID, questionNumber: Int): QuestionInfo {
        var result: QuestionInfo = emptyQuestion

        val response = service.getQuestion(attemptId, questionNumber)

        viewModelScope.launch {
            result = processResponse(response) ?: emptyQuestion
        }

        return result
    }

    suspend fun saveAnswer(attemptId: UUID, questionId: UUID, selectedChoices: List<ChosenAnswer>) {
        val response = service.saveAnswer(attemptId, questionId, selectedChoices)

        viewModelScope.launch {
            processResponse(response)
        }
    }

    suspend fun finishAttempt(attemptId: UUID): AttemptInfo? {
        var result: AttemptInfo? = null
        val response = service.finishAttempt(attemptId)

        viewModelScope.launch {
            result = processResponse(response)
        }

        return result
    }
}