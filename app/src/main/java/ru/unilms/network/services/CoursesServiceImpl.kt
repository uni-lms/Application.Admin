package ru.unilms.network.services

import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import ru.unilms.domain.model.courses.Course
import ru.unilms.domain.model.courses.CourseContent
import ru.unilms.domain.model.error.ErrorResponse
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

}