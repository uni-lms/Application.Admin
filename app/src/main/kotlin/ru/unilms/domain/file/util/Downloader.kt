package ru.unilms.domain.file.util

interface Downloader {
    fun downloadFile(url: String, mimeType: String, fileName: String): Long
}