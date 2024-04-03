package ru.aip.intern.ui.components.form

import java.util.UUID

data class ComboBoxItem(
    val id: UUID,
    val name: String
)

fun <T> List<T>.toComboBoxItemList(
    idSelector: (T) -> UUID,
    nameSelector: (T) -> String
): List<ComboBoxItem> {
    return this.map { item ->
        ComboBoxItem(id = idSelector(item), name = nameSelector(item))
    }
}
