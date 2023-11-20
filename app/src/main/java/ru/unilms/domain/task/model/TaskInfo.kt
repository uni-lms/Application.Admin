package ru.unilms.domain.task.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.LocalDateTimeSerializer
import ru.unilms.domain.common.serialization.UUIDSerializer
import ru.unilms.domain.task.util.enums.TaskStatus
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class TaskInfo(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val title: String,
    val description: String?,
    @Serializable(LocalDateTimeSerializer::class)
    val availableUntil: LocalDateTime,
    val maximumPoints: Int,
    val rating: Int,
    val status: TaskStatus = TaskStatus.NotSent,
)