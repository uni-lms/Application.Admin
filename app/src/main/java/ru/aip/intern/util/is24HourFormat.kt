package ru.aip.intern.util

import android.content.Context
import android.text.format.DateFormat

fun is24HourFormat(context: Context): Boolean {
    return DateFormat.is24HourFormat(context)
}