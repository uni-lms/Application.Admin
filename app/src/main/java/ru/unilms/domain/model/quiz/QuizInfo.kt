package ru.unilms.domain.model.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizInfo(
    val visibleName: String,
    val amountOfQuestions: Int,
    val amountOfAttempts: Int,
    val remainingAttempts: Int,
    val timeLimit: Int,
    val attempts: List<AttemptInfo>,
    val hasStartedAttempt: Boolean
)
