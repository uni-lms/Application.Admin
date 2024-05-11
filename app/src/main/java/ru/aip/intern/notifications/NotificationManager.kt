package ru.aip.intern.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.aip.intern.R
import ru.aip.intern.util.UiText
import java.util.UUID
import javax.inject.Inject

class NotificationManager @Inject constructor(
    private val notificationManager: NotificationManager,
    @ApplicationContext private val context: Context
) {

    fun createChannel(channel: ru.aip.intern.notifications.NotificationChannel) {
        createChannel(channel.id, channel.channelName.asString(context), channel.importance)
    }

    fun buildNotification(
        channel: ru.aip.intern.notifications.NotificationChannel,
        progress: Int?,
        title: UiText,
        description: UiText,
        isOngoing: Boolean

    ): Notification {
        createChannel(channel)

        return buildNotification(
            channel.id,
            progress,
            title.asString(context),
            description.asString(context),
            isOngoing
        )
    }

    fun pushNotification(notification: Notification): Int {
        val notificationId = UUID.randomUUID().hashCode()
        pushNotification(notificationId, notification)

        return notificationId
    }

    fun pushNotification(id: Int, notification: Notification) {
        notificationManager.notify(id, notification)
    }

    private fun createChannel(id: String, name: String, importance: Int) {
        val channel = NotificationChannel(id, name, importance)
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildNotification(
        channelId: String,
        progress: Int?,
        title: String,
        description: String,
        isOngoing: Boolean

    ): Notification {
        return if (progress == null) {
            NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .build()
        } else {
            NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setProgress(100, progress, false)
                .setOngoing(isOngoing)
                .build()
        }
    }


}