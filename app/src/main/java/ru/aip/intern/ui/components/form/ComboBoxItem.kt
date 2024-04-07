package ru.aip.intern.ui.components.form

data class ComboBoxItem<TId>(
    val id: TId,
    val name: String
)

fun <T, TId> List<T>.toComboBoxItemList(
    idSelector: (T) -> TId,
    nameSelector: (T) -> String
): List<ComboBoxItem<TId>> {
    return this.map { item ->
        ComboBoxItem(id = idSelector(item), name = nameSelector(item))
    }
}
