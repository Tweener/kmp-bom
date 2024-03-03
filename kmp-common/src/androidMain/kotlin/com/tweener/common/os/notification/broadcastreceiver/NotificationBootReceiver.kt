package com.tweener.common.os.notification.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * @author Vivien Mahe
 * @since 07/10/2023
 */
abstract class NotificationBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            onBootComplete(context = context, intent = intent)
        }
    }

    /**
     * Called when the system boot has completed..
     */
    abstract fun onBootComplete(context: Context, intent: Intent)
}
