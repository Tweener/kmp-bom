package com.tweener.common.os.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import io.github.aakira.napier.Napier
import java.util.Calendar

/**
 * @author Vivien Mahe
 * @since 07/10/2023
 */
abstract class AlarmScheduler(private val context: Context) {

    private val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    abstract fun getPendingIntent(): PendingIntent

    fun scheduleInexactDaily(calendar: Calendar) {
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            getPendingIntent()
        )

        val hourOfDayAlarm = calendar.get(Calendar.HOUR_OF_DAY)
        val minutesAlarm = calendar.get(Calendar.MINUTE)

        Napier.d { "Alarm set to display a new notification every day at $hourOfDayAlarm:$minutesAlarm." }
    }

    fun cancel() {
        alarmManager.cancel(getPendingIntent())
    }
}
