package ru.unilms.domain.task.network

import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.task.model.SolutionRequestBody
import java.util.UUID

interface TaskService {
    suspend fun uploadSolution(
        taskId: UUID,
        body: SolutionRequestBody,
    ): Response<Any, ErrorResponse>
}