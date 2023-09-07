package ru.unilms.domain.model.user

import ch.benlu.composeform.fields.PickerValue

data class Gender(val name: String): PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.name.startsWith(query)
    }
}