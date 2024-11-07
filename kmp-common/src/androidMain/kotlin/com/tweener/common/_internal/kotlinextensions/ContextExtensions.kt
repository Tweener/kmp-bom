package com.tweener.common._internal.kotlinextensions

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context

/**
 * @author Vivien Mahe
 * @since 06/11/2024
 */

fun Context.getAlarmManager(): AlarmManager? = getSystemService(Context.ALARM_SERVICE) as? AlarmManager

fun Context.getNotificationManager(): NotificationManager? = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
