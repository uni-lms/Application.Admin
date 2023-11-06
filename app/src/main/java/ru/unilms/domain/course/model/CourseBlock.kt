package ru.unilms.domain.course.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.course.util.enums.CourseBlockName

@Serializable
data class CourseBlock(val title: CourseBlockName, val items: List<CourseItem>)
