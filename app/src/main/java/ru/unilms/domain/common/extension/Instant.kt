package ru.unilms.domain.common.extension

import ru.unilms.domain.common.serialization.InstantSerializer
import java.time.Instant

fun Instant.formatAsString(): String = InstantSerializer.formatAsString(this)