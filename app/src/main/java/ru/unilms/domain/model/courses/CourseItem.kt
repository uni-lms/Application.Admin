package ru.unilms.domain.model.courses

import ru.unilms.utils.enums.CourseItemType
import java.util.UUID

data class CourseItem(val id: UUID, val visibleName: String, val type: CourseItemType)
