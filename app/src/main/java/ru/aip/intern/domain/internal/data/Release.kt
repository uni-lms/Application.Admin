package ru.aip.intern.domain.internal.data

import kotlinx.serialization.Serializable

@Serializable
data class Release(
    val version: String
)
