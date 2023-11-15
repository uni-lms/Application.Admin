package ru.unilms.domain.course.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import ru.unilms.domain.course.util.enums.CourseItemType
import java.util.UUID

@Serializable
data class CourseItem(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val visibleName: String,
    val type: CourseItemType,
)
