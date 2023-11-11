package ru.unilms.domain.common.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class BaseModel(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
)
