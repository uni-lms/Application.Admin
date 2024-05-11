package ru.aip.intern.domain.content.quiz.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class QuizPassAttempt(
    @Serializable(LocalDateTimeSerializer::class)
    val startedAt: LocalDateTime,
    @Serializable(LocalDateTimeSerializer::class)
    val finishedAt: LocalDateTime?,
    val accruedPoints: AccruedPoints
)

fun QuizPassAttempt.isFinished(): Boolean {
    return finishedAt != null
}

fun QuizPassAttempt.isNotFinished(): Boolean {
    return !isFinished()
}