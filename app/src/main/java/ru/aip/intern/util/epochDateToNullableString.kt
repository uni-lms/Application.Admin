package ru.aip.intern.util

fun Long?.epochDateToNullableString(): String? {
    if (this == null) {
        return null
    }

    return this.toLocalDateTime().formatDate()
}