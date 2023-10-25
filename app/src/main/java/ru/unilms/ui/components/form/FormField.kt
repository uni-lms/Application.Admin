package ru.unilms.ui.components.form

import java.util.UUID

enum class FieldType {
    RadioButton,
    Checkbox
}

data class FormField(val label: String, val id: UUID, val type: FieldType)
