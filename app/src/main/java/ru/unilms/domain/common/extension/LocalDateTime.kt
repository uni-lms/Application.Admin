package ru.unilms.domain.common.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.formatAsString(): String = this.format(
    DateTimeFormatter.ofPattern(
        "dd.MM.yyyy HH:mm",
        Locale.getDefault()
    )
)