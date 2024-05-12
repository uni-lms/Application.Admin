package ru.aip.intern.domain.content.quiz.data

import kotlinx.serialization.Serializable

@Serializable
data class QuestionInfo(
    val text: String = "",
    val isMultipleChoicesAllowed: Boolean = false,
    val choices: List<Choice> = emptyList(),
    val amountOfQuestions: Int = 0,
)
