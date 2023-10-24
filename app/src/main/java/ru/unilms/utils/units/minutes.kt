package ru.unilms.utils.units

import java.time.Duration
import java.time.LocalTime

inline val Int.minutes: String get() = Minutes(value = this).toString()

data class Minutes(val value: Int) {
    override fun toString(): String {
        return LocalTime.MIN.plus(
            Duration.ofMinutes(value.toLong())
        ).toString()
    }
}
