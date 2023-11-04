package ru.unilms.domain.model.courses

import kotlinx.serialization.Serializable
import ru.unilms.utils.enums.CourseItemType
import ru.unilms.utils.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class CourseItem(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val visibleName: String,
    val type: CourseItemType
)
