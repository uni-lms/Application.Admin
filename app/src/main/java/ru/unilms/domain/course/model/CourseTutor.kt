package ru.unilms.domain.course.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class CourseTutor(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val abbreviation: String,
    val semester: Int,
    val groups: List<String>,
)

// WGNkLXwP14LFl7BcfQ==
