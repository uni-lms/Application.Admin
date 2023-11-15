package ru.unilms.domain.auth.model

import ch.benlu.composeform.fields.PickerValue
import java.util.UUID

data class Gender(val id: UUID, val name: String): PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.name.startsWith(query)
    }
}