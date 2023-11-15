package ru.unilms.domain.common.form.dynamic

import java.util.UUID

data class FormField(val label: String, val id: UUID, val type: FieldType)
