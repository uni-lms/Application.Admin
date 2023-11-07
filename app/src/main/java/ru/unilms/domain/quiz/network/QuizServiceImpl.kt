package ru.unilms.domain.quiz.network

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.common.network.safeRequest
import ru.unilms.domain.quiz.model.QuestionChoice
import ru.unilms.domain.quiz.model.QuestionInfo
import ru.unilms.domain.quiz.model.QuizInfo
import java.util.UUID

class QuizServiceImpl(val token: String) : QuizService {
    override suspend fun getQuizInfo(quizId: UUID): Response<QuizInfo, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/materials/quiz/${quizId}/details")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun getQuestion(questionId: UUID): Response<QuestionInfo, ErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAnswer(
        questionId: UUID,
        selectedChoices: List<QuestionChoice>
    ): Response<Nothing, ErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun startAttempt(quizId: UUID): Response<UUID, ErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun finishAttempt(attemptId: UUID): Response<Nothing, ErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAttemptResults(attemptId: UUID): Response<Nothing, ErrorResponse> {
        TODO("Not yet implemented")
    }
}