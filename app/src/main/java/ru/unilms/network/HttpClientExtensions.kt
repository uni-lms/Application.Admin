package ru.unilms.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T, reified E> HttpClient.safeRequest(block: HttpRequestBuilder.() -> Unit): Response<T, E> {
    return try {
        val response = request { block() }
        Response.Success(response.body())
    } catch (e: ClientRequestException) {
        Response.Error.HttpError(e.response.status.value, e.errorBody())
    } catch (e: ServerResponseException) {
        Response.Error.HttpError(e.response.status.value, e.errorBody())
    } catch (e: IOException) {
        Response.Error.NetworkError
    } catch (e: SerializationException) {
        Response.Error.SerializationError
    }
}

suspend inline fun <reified E> ResponseException.errorBody(): E? {
    return try {
        response.body()
    } catch (e: SerializationException) {
        null
    }
}