package ru.aip.intern.util

import android.content.Context
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Pair<Int, Int>.formatTimeFromPair(context: Context): String {
    val is24HourFormat = is24HourFormat(context)
    val pattern = if (is24HourFormat) "HH:mm" else "hh:mm a"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val time = LocalTime.of(this.first, this.second)

    return time.format(formatter)
}