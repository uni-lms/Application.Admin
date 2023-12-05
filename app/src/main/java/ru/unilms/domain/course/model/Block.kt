package ru.unilms.domain.course.model

import ch.benlu.composeform.fields.PickerValue
import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import ru.unilms.domain.course.util.enums.CourseBlockName
import java.util.UUID

@Serializable
data class Block(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val name: CourseBlockName,
) : PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return true
    }

}
