package ru.aip.intern.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.safeRequest(block: HttpRequestBuilder.() -> Unit): Response<T> {
    return try {
        val response = request {
            block()
            header(HttpHeaders.Accept, ContentType.Application.Json)
        }

        when (response.status) {
            HttpStatusCode.OK -> Response(
                isSuccess = true,
                value = response.body(),
                errorMessage = null
            )

            HttpStatusCode.Unauthorized -> Response(
                isSuccess = false,
                errorMessage = response.headers["www-authenticate"] ?: "Не авторизован",
                value = null
            )

            else -> {
                Response(isSuccess = false, value = null, errorMessage = "Неизвестная ошибка")
            }
        }

    } catch (e: ClientRequestException) {
        Response(isSuccess = false, value = null, errorMessage = "Ошибка на клиенте")
    } catch (e: ServerResponseException) {
        Response(isSuccess = false, value = null, errorMessage = "Ошибка на сервере")
    } catch (e: IOException) {
        Response(isSuccess = false, value = null, errorMessage = "Ошибка сети")
    } catch (e: SerializationException) {
        Response(isSuccess = false, value = null, errorMessage = "Ошибка парсинга данных")
    }
}

suspend inline fun <reified E> ResponseException.errorBody(): E? {
    return try {
        response.body()
    } catch (e: SerializationException) {
        null
    }
}