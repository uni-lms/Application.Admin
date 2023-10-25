package ru.unilms.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.model.quiz.QuestionChoice
import ru.unilms.domain.model.quiz.QuestionInfo
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel() {
    fun getQuestionInfo(questionNumber: Int): QuestionInfo {
        return QuestionInfo(
            10,
            "Тест",
            "Тут типа какой-то вопрос",
            questionNumber % 2 == 0,
            listOf(
                QuestionChoice(
                    UUID.randomUUID(),
                    "Ответ ${questionNumber + 1}"
                ),
                QuestionChoice(
                    UUID.randomUUID(),
                    "Ответ ${questionNumber + 2}"
                ),
                QuestionChoice(
                    UUID.randomUUID(),
                    "Ответ ${questionNumber + 3}"
                ),
                QuestionChoice(
                    UUID.randomUUID(),
                    "Ответ ${questionNumber + 4}"
                ),
            )
        )
    }
}