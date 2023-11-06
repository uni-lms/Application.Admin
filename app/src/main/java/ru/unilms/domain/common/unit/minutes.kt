package ru.unilms.domain.common.unit

import java.time.Duration

inline val Int.minutes: String get() = Minutes(value = this).toString()

data class Minutes(val value: Int) {
    override fun toString(): String {
        val duration = Duration.ofMinutes(value.toLong())
        return when (duration.toHours()) {
            0L -> "${duration.toMinutes()} минут"
            else -> "${duration.toHours()} часов ${duration.toMinutes()} минут"
        }
    }
}
