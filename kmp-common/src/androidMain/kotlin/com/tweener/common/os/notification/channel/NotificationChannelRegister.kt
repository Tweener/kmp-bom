package com.tweener.common.os.notification.channel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import io.github.aakira.napier.Napier

/**
 * @author Vivien Mahe
 * @since 05/10/2023
 */
class NotificationChannelRegister(
    private val context: Context,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun register(channel: NotificationChannel) {
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        Napier.d { "Notification channel \"${channel.name}\" (ID: \"${channel.id}\") registered!" }
    }
}
