package ru.unilms.network.services.courses

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
import ru.unilms.domain.model.courses.Course
import ru.unilms.domain.model.courses.CourseContent
import ru.unilms.domain.model.courses.FileContentInfo
import ru.unilms.domain.model.error.ErrorResponse
import ru.unilms.network.HttpClientFactory
import ru.unilms.network.Response
import ru.unilms.network.errorBody
import ru.unilms.network.safeRequest
import ru.unilms.utils.enums.CourseType
import java.util.UUID

class CoursesServiceImpl(
    private val token: String
) : CoursesService {
    override suspend fun getEnrolled(type: CourseType): Response<List<Course>, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            parameter("filter", type.value)
            url("${HttpClientFactory.baseUrl}/v2/courses/enrolled")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun getCourseContents(courseId: UUID): Response<CourseContent, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/courses/${courseId}/contents")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun getTextContent(textId: UUID): Response<ByteArray, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return try {
            val response = client.request {
                method = HttpMethod.Get
                url("${HttpClientFactory.baseUrl}/v1/materials/text/${textId}")
                accept(ContentType.Text.Any)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
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

    override suspend fun getTextContentInfo(textId: UUID): Response<FileContentInfo, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/materials/text/${textId}/info")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

}