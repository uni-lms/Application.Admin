package ru.unilms.domain.task.network

import io.ktor.client.request.forms.FormPart
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.common.network.safeRequest
import ru.unilms.domain.task.model.SolutionRequestBody
import java.util.UUID

class TaskServiceImpl(val token: String) : TaskService {
    override suspend fun uploadSolution(
        taskId: UUID,
        body: SolutionRequestBody,
    ): Response<Any, ErrorResponse> {
        val client = HttpClientFactory.httpClient
        return client.safeRequest {
            method = HttpMethod.Post
            setBody(
                MultiPartFormDataContent(
                    formData {
                        body.files.forEach {
                            appendInput(
                                key = "",
                                headers = Headers.build {
                                    append(HttpHeaders.ContentDisposition, "filename=${it.name}")
                                },
                                size = it.length()
                            ) {
                                buildPacket {
                                    writeFully(it.readBytes())
                                }
                            }
                        }
                        append(FormPart("isReadyForCheck", body.isReadyForCheck))
                    }
                )
            )
        }
    }
}