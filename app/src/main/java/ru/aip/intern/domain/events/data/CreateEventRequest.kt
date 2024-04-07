package ru.aip.intern.domain.events.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.LocalDateTimeSerializer
import ru.aip.intern.serialization.UuidSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Serializable
data class CreateEventRequest(
    val title: String,
    @Serializable(LocalDateTimeSerializer::class)
    val startTimestamp: LocalDateTime,
    @Serializable(LocalDateTimeSerializer::class)
    val endTimeStamp: LocalDateTime,
    val link: String?,
    val invitedInternships: List<@Serializable(UuidSerializer::class) UUID>,
    val invitedUsers: List<@Serializable(UuidSerializer::class) UUID>,
    val type: String
) {
    override fun toString(): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return """
            CreateEventRequest(
                title = '$title',
                startTimestamp = '${startTimestamp.format(dateFormatter)}',
                endTimeStamp = '${endTimeStamp.format(dateFormatter)}',
                link = '${link ?: "None"}',
                invitedInternships = ${
            invitedInternships.joinToString(
                prefix = "[",
                postfix = "]"
            )
        },
                invitedUsers = ${invitedUsers.joinToString(prefix = "[", postfix = "]")},
                type = '$type'
            )
        """.trimIndent()
    }
}
