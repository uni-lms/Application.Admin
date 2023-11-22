package ru.unilms.domain.common.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object InstantSerializer : KSerializer<Instant> {
    override val descriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)
    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault())
    private val readableFormatter: DateTimeFormatter = DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm")
        .withZone(ZoneId.systemDefault())

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.from(formatter.parse(decoder.decodeString()))
    }

    fun formatAsString(value: Instant): String {
        return readableFormatter.format(value)
    }
}
