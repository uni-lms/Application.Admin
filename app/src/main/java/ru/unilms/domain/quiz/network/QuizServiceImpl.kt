package ru.unilms.domain.quiz.network

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import ru.unilms.domain.common.model.BaseModel
import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.common.network.safeRequest
import ru.unilms.domain.quiz.model.AttemptInfoDto
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

    override suspend fun getQuestion(
        attemptId: UUID,
        questionNumber: Int,
    ): Response<QuestionInfo, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/quiz-attempt/${attemptId}/question/${questionNumber}")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun saveAnswer(
        questionId: UUID,
        selectedChoices: List<QuestionChoice>,
    ): Response<Nothing, ErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun startAttempt(quizId: UUID): Response<AttemptInfoDto, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Post
            url("${HttpClientFactory.baseUrl}/v1/materials/quiz/start-attempt")
            setBody(BaseModel(quizId))
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun finishAttempt(attemptId: UUID): Response<Nothing, ErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAttemptResults(attemptId: UUID): Response<Nothing, ErrorResponse> {
        TODO("Not yet implemented")
    }
}