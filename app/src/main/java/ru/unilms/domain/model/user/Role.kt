package ru.unilms.domain.model.user

import ch.benlu.composeform.fields.PickerValue
import java.util.UUID

data class Role(val id: UUID, val name: String): PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.name.startsWith(query)
    }
}