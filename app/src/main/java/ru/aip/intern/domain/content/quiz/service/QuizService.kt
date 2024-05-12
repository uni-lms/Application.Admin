package ru.aip.intern.domain.content.quiz.service

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import ru.aip.intern.auth.AuthManager
import ru.aip.intern.domain.common.IdModel
import ru.aip.intern.domain.content.quiz.data.QuestionInfo
import ru.aip.intern.domain.content.quiz.data.QuizInfo
import ru.aip.intern.domain.content.quiz.data.StartAttemptRequest
import ru.aip.intern.networking.HttpClientFactory
import ru.aip.intern.networking.Response
import ru.aip.intern.networking.safeRequest
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

class QuizService @Inject constructor(
    private val authManager: AuthManager
) {
    suspend fun getQuizInfo(id: UUID): Response<QuizInfo> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/quizzes/${id}")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
                append(HttpHeaders.AcceptLanguage, Locale.getDefault().toLanguageTag())
            }
        }
    }

    suspend fun startAttempt(id: UUID): Response<IdModel> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Post
            url("/quizzes/attempts")
            accept(ContentType.Application.Json)
            setBody(StartAttemptRequest(quiz = id))
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
            }
        }
    }

    suspend fun getQuestion(attemptId: UUID, question: Int): Response<QuestionInfo> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/quizzes/question/${attemptId}/${question}")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
            }
        }
    }
}