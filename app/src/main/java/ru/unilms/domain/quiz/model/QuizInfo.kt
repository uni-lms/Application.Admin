package ru.unilms.domain.quiz.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.quiz.model.AttemptInfo

@Serializable
data class QuizInfo(
    val visibleName: String,
    val amountOfQuestions: Int,
    val amountOfAttempts: Int,
    val remainingAttempts: Int,
    val timeLimit: Int?,
    val attempts: List<AttemptInfo>,
    val hasStartedAttempt: Boolean,
    val maximumPoints: Int
)
