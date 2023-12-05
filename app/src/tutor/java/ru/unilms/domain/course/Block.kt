package ru.unilms.domain.course

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class Block(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val name: String,
)
