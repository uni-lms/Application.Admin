package ru.aip.intern.domain.events.service

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import ru.aip.intern.auth.AuthManager
import ru.aip.intern.domain.events.data.CustomEvent
import ru.aip.intern.domain.events.data.EventCreatingInfo
import ru.aip.intern.networking.HttpClientFactory
import ru.aip.intern.networking.Response
import ru.aip.intern.networking.safeRequest
import java.util.UUID
import javax.inject.Inject

class EventsService @Inject constructor(private val authManager: AuthManager) {

    suspend fun getEvent(id: UUID): Response<CustomEvent> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/events/${id}")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
            }
        }
    }

    suspend fun getDataForEventCreating(): Response<EventCreatingInfo> {
        val httpClient = HttpClientFactory.httpClient
        val authHeaderValue = authManager.getAuthHeaderValue()
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("/events/required-info")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, authHeaderValue)
            }
        }
    }
}