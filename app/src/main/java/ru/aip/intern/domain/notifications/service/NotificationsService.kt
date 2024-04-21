package ru.aip.intern.domain.notifications.service

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import ru.aip.intern.auth.AuthManager
import ru.aip.intern.domain.notifications.data.NotificationsList
import ru.aip.intern.networking.HttpClientFactory
import ru.aip.intern.networking.Response
import ru.aip.intern.networking.safeRequest
import javax.inject.Inject

class NotificationsService @Inject constructor(private val authManager: AuthManager) {
    suspend fun getNotifications(): Response<NotificationsList> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/notifications")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
            }
        }
    }
}