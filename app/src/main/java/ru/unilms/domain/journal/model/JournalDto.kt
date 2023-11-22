package ru.unilms.domain.journal.model

import kotlinx.serialization.Serializable

@Serializable
data class JournalDto(
    val courseName: String,
    val items: List<JournalItem>,
)
