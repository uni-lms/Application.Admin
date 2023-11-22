package ru.unilms.domain.journal.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import ru.unilms.domain.course.util.enums.CourseItemType
import java.util.UUID

@Serializable
data class JournalItem(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val type: CourseItemType,
    val status: String,
)
