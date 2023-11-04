package ru.unilms.domain.model.courses

import kotlinx.serialization.Serializable

@Serializable
data class CourseContent(
    val name: String,
    val abbreviation: String,
    val semester: Int,
    val blocks: List<CourseBlock>
)
