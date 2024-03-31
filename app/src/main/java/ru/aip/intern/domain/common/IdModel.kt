package ru.aip.intern.domain.common

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class IdModel(
    @Serializable(UuidSerializer::class)
    val id: UUID
)
