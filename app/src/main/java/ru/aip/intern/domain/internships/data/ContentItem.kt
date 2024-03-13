package ru.aip.intern.domain.internships.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.ContentTypeSerializer
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class ContentItem(
    @Serializable(UuidSerializer::class)
    val id: UUID,

    @Serializable(ContentTypeSerializer::class)
    val type: ContentType
)
