package ru.aip.intern.domain.content.assignment.service

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
import ru.aip.intern.domain.content.assignment.data.AssignmentInfo
import ru.aip.intern.domain.content.assignment.data.CreateCommentRequest
import ru.aip.intern.domain.content.assignment.data.SolutionInfo
import ru.aip.intern.domain.content.assignment.data.SolutionsList
import ru.aip.intern.networking.HttpClientFactory
import ru.aip.intern.networking.Response
import ru.aip.intern.networking.safeRequest
import java.util.UUID
import javax.inject.Inject

class AssignmentService @Inject constructor(private val authManager: AuthManager) {
    suspend fun getInfo(id: UUID): Response<AssignmentInfo> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/content/assignments/${id}")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
            }
        }
    }

    suspend fun getSolutionsInfo(id: UUID): Response<SolutionsList> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/content/assignments/${id}/solutions")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
            }
        }
    }

    suspend fun getSolution(id: UUID): Response<SolutionInfo> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/content/solutions/${id}")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
            }
        }
    }

    suspend fun createComment(id: UUID, text: String, replyToCommentId: UUID?): Response<IdModel> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Post
            url("/content/solutions/${id}/comments")
            setBody(CreateCommentRequest(text, replyToCommentId))
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
            }
        }
    }
}