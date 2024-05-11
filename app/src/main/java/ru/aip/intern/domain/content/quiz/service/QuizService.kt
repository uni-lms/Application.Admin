package ru.aip.intern.domain.content.quiz.service

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import ru.aip.intern.auth.AuthManager
import ru.aip.intern.domain.content.quiz.data.QuizInfo
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
}