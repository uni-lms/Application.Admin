package ru.unilms.domain.course.network

import android.webkit.MimeTypeMap
import androidx.core.net.toFile
import io.ktor.client.request.accept
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.common.network.safeRequest
import ru.unilms.domain.course.model.Block
import ru.unilms.domain.course.model.Course
import ru.unilms.domain.course.model.CourseContent
import ru.unilms.domain.course.model.CourseTutor
import ru.unilms.domain.course.model.CreateCourseRequest
import ru.unilms.domain.course.model.CreateFileRequest
import ru.unilms.domain.course.model.FileResponse
import ru.unilms.domain.course.model.TextContentInfo
import ru.unilms.domain.course.util.enums.CourseType
import ru.unilms.domain.file.model.FileContentInfo
import ru.unilms.domain.journal.model.JournalDto
import ru.unilms.domain.manage.model.Group
import ru.unilms.domain.task.model.TaskInfo
import java.util.UUID

class CoursesServiceImpl(
    private val token: String,
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

    override suspend fun getOwned(): Response<List<CourseTutor>, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/courses/owned")
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
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/materials/text/${textId}")
            accept(ContentType.Text.Any)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun getTextContentInfo(textId: UUID): Response<TextContentInfo, ErrorResponse> {
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

    override suspend fun getFileContentInfo(fileId: UUID): Response<FileContentInfo, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/materials/file/${fileId}/info")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun getTaskInfo(taskId: UUID): Response<TaskInfo, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/assignments/${taskId}")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun getJournal(courseId: UUID): Response<JournalDto, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/course/${courseId}/journal")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun getBlocks(): Response<List<Block>, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/blocks")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun getGroups(): Response<List<Group>, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Get
            url("${HttpClientFactory.baseUrl}/v1/groups")
            accept(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun createCourse(request: CreateCourseRequest): Response<Course, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Post
            url("${HttpClientFactory.baseUrl}/v1/courses")
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            setBody(request)
        }
    }

    override suspend fun createFile(request: CreateFileRequest): Response<FileResponse, ErrorResponse> {
        val client = HttpClientFactory.httpClient

        val file = request.fileUri.toFile()
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
        val body = MultiPartFormDataContent(
            formData {
                append("blockId", request.blockId.toString())
                append("isVisibleToStudents", request.isVisibleToStudents)
                append("visibleName", request.visibleName)
                append("content", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, mimeType!!)
                    append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                })
            }
        )

        return client.safeRequest {
            method = HttpMethod.Post
            url("${HttpClientFactory.baseUrl}/v1/courses/${request.courseId}/file")
            accept(ContentType.Application.Json)
            contentType(ContentType.MultiPart.FormData)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            setBody(body)
        }
    }

}