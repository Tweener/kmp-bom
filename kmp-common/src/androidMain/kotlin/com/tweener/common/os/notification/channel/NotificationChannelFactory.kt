package com.tweener.common.os.notification.channel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import io.github.aakira.napier.Napier

/**
 * @author Vivien Mahe
 * @since 05/10/2023
 */
class NotificationChannelFactory {

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun create(
            channelId: String,
            name: String,
            importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
            descriptionText: String? = null
        ): NotificationChannel =
            NotificationChannel(channelId, name, importance)
                .apply { description = descriptionText }
                .also { Napier.d { "Notification channel \"${it.name}\" (ID: \"${it.id}\") created!" } }
    }
}
