package ru.aip.intern.ui.state

import ru.aip.intern.domain.content.quiz.data.QuizInfo
import ru.aip.intern.domain.internships.data.UserRole
import java.time.LocalDateTime
import java.util.UUID

data class QuizState(
    val isRefreshing: Boolean = false,
    val isStartAttemptDialogVisible: Boolean = false,
    val role: UserRole = UserRole.Intern,
    val quizInfo: QuizInfo = QuizInfo(
        id = UUID.randomUUID(),
        internshipId = UUID.randomUUID(),
        title = "",
        description = null,
        allowedAttempts = 1,
        availableUntil = LocalDateTime.now(),
        timeLimit = "",
        attempts = emptyList()
    )
)