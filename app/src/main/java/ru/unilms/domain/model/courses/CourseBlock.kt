package ru.unilms.domain.model.courses

import kotlinx.serialization.Serializable
import ru.unilms.utils.enums.CourseBlockName

@Serializable
data class CourseBlock(val title: CourseBlockName, val items: List<CourseItem>)
