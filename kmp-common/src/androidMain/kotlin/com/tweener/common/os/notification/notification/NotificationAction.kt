package com.tweener.common.os.notification.notification

import android.app.PendingIntent
import androidx.annotation.DrawableRes

/**
 * @author Vivien Mahe
 * @since 14/12/2023
 */
data class NotificationAction(
    val title: CharSequence,
    @DrawableRes val icon: Int,
    val pendingIntent: PendingIntent? = null
)
