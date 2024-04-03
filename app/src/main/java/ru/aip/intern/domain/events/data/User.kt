package ru.aip.intern.domain.events.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class User(
    @Serializable(UuidSerializer::class)
    val id: UUID,
    val name: String,
)