package ru.aip.intern.ui.state

import ru.aip.intern.domain.content.quiz.data.QuizInfo
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

data class QuizState(
    val isRefreshing: Boolean = false,
    val quizInfo: QuizInfo = QuizInfo(
        UUID.randomUUID(),
        title = "",
        null,
        allowedAttempts = 1,
        availableUntil = LocalDateTime.now(),
        timeLimit = Duration.ZERO,
        attempts = emptyList()
    )
)