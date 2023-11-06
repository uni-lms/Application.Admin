package ru.unilms.network.services

import ru.unilms.domain.model.error.ErrorResponse
import ru.unilms.domain.model.quiz.QuestionChoice
import ru.unilms.domain.model.quiz.QuestionInfo
import ru.unilms.domain.model.quiz.QuizInfo
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