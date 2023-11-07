package ru.unilms.domain.quiz.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.processResponse
import ru.unilms.domain.quiz.model.QuizInfo
import ru.unilms.domain.quiz.network.QuizServiceImpl
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class QuizInfoViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {
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

    suspend fun getQuizInfo(quizId: UUID): QuizInfo? {
        var result: QuizInfo? = null

        val response = service.getQuizInfo(quizId)

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result
    }
}