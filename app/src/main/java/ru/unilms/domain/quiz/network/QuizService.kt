package ru.unilms.domain.quiz.network

import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.quiz.model.AttemptInfoDto
import ru.unilms.domain.quiz.model.ChosenAnswer
import ru.unilms.domain.quiz.model.QuestionInfo
import ru.unilms.domain.quiz.model.QuizInfo
import ru.unilms.domain.quiz.model.SaveAnswerResponse
import java.util.UUID

interface QuizService {
    suspend fun getQuizInfo(quizId: UUID): Response<QuizInfo, ErrorResponse>
    suspend fun getQuestion(
        attemptId: UUID,
        questionNumber: Int,
    ): Response<QuestionInfo, ErrorResponse>

    suspend fun saveAnswer(
        attemptId: UUID,
        questionId: UUID,
        selectedChoices: List<ChosenAnswer>,
    ): Response<SaveAnswerResponse, ErrorResponse>

    suspend fun startAttempt(quizId: UUID): Response<AttemptInfoDto, ErrorResponse>
    suspend fun finishAttempt(attemptId: UUID): Response<Nothing, ErrorResponse>
    suspend fun getAttemptResults(attemptId: UUID): Response<Nothing, ErrorResponse>
}