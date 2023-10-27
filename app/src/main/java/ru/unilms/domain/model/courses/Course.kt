package ru.unilms.domain.model.courses

import kotlinx.serialization.Serializable
import ru.unilms.utils.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class Course(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val abbreviation: String,
    val progress: Float,
    val semester: Int,
    val tutors: List<String>
)