package ru.unilms.domain.model.user

import ch.benlu.composeform.fields.PickerValue

data class Role(val name: String): PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.name.startsWith(query)
    }
}