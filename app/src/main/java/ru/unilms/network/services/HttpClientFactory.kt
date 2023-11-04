package ru.unilms.network.services

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

object HttpClientFactory {
    var baseUrl = ""
    val httpClient: HttpClient by lazy {
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json()
            }
        }
    }
}