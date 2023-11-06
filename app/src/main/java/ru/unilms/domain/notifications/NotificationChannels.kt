package ru.unilms.domain.notifications

import android.app.NotificationManager
import ru.unilms.R

enum class NotificationChannels(
    val id: Int,
    val stringId: String,
    val title: Int,
    val description: Int,
    val importance: Int
) {
    Downloads(
        0,
        "uni_app_downloads_channel",
        R.string.notification_channel_downloads_name,
        R.string.notification_channel_downloads_description,
        NotificationManager.IMPORTANCE_LOW
    )
}