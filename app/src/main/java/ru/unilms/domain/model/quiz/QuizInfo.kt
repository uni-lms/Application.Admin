package ru.unilms.domain.model.quiz

data class QuizInfo(
    val visibleName: String,
    val amountOfQuestions: Int,
    val amountOfAttempts: Int,
    val timeLimit: Int
)
