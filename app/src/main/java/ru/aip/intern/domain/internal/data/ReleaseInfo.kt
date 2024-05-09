package ru.aip.intern.domain.internal.data

import kotlinx.serialization.Serializable

@Serializable
data class ReleaseInfo(
    val title: String,
    val body: List<String>,
    val downloadUrl: String,
    val contentType: String,
    val fileName: String
)
