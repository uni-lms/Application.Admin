package ru.unilms.domain.quiz.network

import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.quiz.view.screen.QuestionChoice
import ru.unilms.domain.quiz.view.screen.QuestionInfo
import ru.unilms.domain.quiz.view.screen.QuizInfo
import java.util.UUID

interface QuizService {
    suspend fun getQuizInfo(quizId: UUID): Response<QuizInfo, ErrorResponse>
    suspend fun getQuestion(questionId: UUID): Response<QuestionInfo, ErrorResponse>
    suspend fun saveAnswer(
        questionId: UUID,
        selectedChoices: List<QuestionChoice>
    ): Response<Nothing, ErrorResponse>

    suspend fun startAttempt(quizId: UUID): Response<UUID, ErrorResponse>
    suspend fun finishAttempt(attemptId: UUID): Response<Nothing, ErrorResponse>
    suspend fun getAttemptResults(attemptId: UUID): Response<Nothing, ErrorResponse>
}