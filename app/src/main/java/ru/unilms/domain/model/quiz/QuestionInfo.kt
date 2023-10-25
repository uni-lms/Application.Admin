package ru.unilms.domain.model.quiz

data class QuestionInfo(
    val amountOfQuestions: Int,
    val quizTitle: String,
    val questionTitle: String,
    val isMultipleChoicesAllowed: Boolean,
    val answers: List<QuestionChoice>
)
