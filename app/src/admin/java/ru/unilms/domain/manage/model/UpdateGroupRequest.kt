package ru.unilms.domain.manage.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class UpdateGroupRequest(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val currentSemester: Int,
    val maxSemester: Int,
)
