package ru.unilms.domain.course.model

import kotlinx.serialization.Serializable

@Serializable
data class CourseContent(
    val name: String,
    val abbreviation: String,
    val semester: Int,
    val blocks: List<CourseBlock>
)
