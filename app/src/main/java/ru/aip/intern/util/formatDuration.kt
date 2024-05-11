package ru.aip.intern.util

import android.icu.text.MeasureFormat
import android.icu.util.MeasureUnit
import java.text.NumberFormat
import java.time.Duration
import java.util.Locale
import kotlin.Long
import kotlin.String
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toKotlinDuration

fun Duration.format(
    locale: Locale = Locale.getDefault(),
): String {
    val formattedStringComponents = mutableListOf<String>()
    var remainder = this.toKotlinDuration()

    for (unit in Unit.entries) {
        val component = calculateComponent(unit, remainder)

        remainder = when (unit) {
            Unit.DAY -> remainder - component.days
            Unit.HOUR -> remainder - component.hours
            Unit.MINUTE -> remainder - component.minutes
            Unit.SECOND -> remainder - component.seconds
        }

        val unitDisplayName = unitDisplayName(unit, locale)

        if (component > 0) {
            val formattedComponent = NumberFormat.getInstance(locale).format(component)
            formattedStringComponents.add("$formattedComponent $unitDisplayName")
        }

        if (unit == Unit.SECOND) {
            val formattedZero = NumberFormat.getInstance(locale).format(0)
            if (formattedStringComponents.isEmpty()) formattedStringComponents.add("$formattedZero$unitDisplayName")
            break
        }
    }

    return formattedStringComponents.joinToString(" ")
}

private fun calculateComponent(unit: Unit, remainder: kotlin.time.Duration): Long {
    return when (unit) {
        Unit.DAY -> remainder.inWholeDays
        Unit.HOUR -> remainder.inWholeHours
        Unit.MINUTE -> remainder.inWholeMinutes
        Unit.SECOND -> remainder.inWholeSeconds
    }
}

private fun unitDisplayName(unit: Unit, locale: Locale): String? {
    val measureFormat = MeasureFormat.getInstance(locale, MeasureFormat.FormatWidth.NARROW)
    return when (unit) {
        Unit.DAY -> measureFormat.getUnitDisplayName(MeasureUnit.DAY)
        Unit.HOUR -> measureFormat.getUnitDisplayName(MeasureUnit.HOUR)
        Unit.MINUTE -> measureFormat.getUnitDisplayName(MeasureUnit.MINUTE)
        Unit.SECOND -> measureFormat.getUnitDisplayName(MeasureUnit.SECOND)
    }
}

private enum class Unit {
    DAY, HOUR, MINUTE, SECOND
}