package ru.unilms.domain.quiz.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionInfo(
    val amountOfQuestions: Int,
    val quizTitle: String,
    val questionTitle: String,
    val isMultipleChoicesAllowed: Boolean,
    val answers: List<QuestionChoice>
)
