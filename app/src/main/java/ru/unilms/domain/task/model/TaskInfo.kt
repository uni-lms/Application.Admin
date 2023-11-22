package ru.unilms.domain.task.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.InstantSerializer
import ru.unilms.domain.common.serialization.UUIDSerializer
import ru.unilms.domain.task.util.enums.TaskStatus
import java.time.Instant
import java.util.UUID

@Serializable
data class TaskInfo(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val title: String,
    val description: String?,
    @Serializable(InstantSerializer::class)
    val availableUntil: Instant,
    val maximumPoints: Int,
    val rating: Int,
    val status: TaskStatus = TaskStatus.NotSent,
)