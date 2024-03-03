package com.tweener.common.os.notification.notification

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tweener.common.os.permission.PermissionChecker
import io.github.aakira.napier.Napier
import kotlin.random.Random

/**
 * @author Vivien Mahe
 * @since 05/10/2023
 */
class NotificationNotifier(
    private val context: Context,
    private val permissionChecker: PermissionChecker
) {

    /**
     * Shows a notification configured with the given [notificationBuilder]. This notification can be identified via its ID, given by [notificationId] or a generated one.
     */
    @SuppressLint("MissingPermission")
    fun notify(notificationBuilder: NotificationCompat.Builder, notificationId: Int = Random.nextInt()) {
        with(NotificationManagerCompat.from(context)) {
            // Check if permission for notifications is granted
            if (permissionChecker.isGranted(Manifest.permission.POST_NOTIFICATIONS).not()) {
                Napier.d { "Cannot show notifications: POST_NOTIFICATIONS permission not granted!" }
                return
            }

            // Show the notification
            notify(notificationId, notificationBuilder.build())
        }
    }
}
