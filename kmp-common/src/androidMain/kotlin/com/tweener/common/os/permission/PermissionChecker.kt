package com.tweener.common.os.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

/**
 * @author Vivien Mahe
 * @since 06/10/2023
 */
class PermissionChecker(
    private val context: Context
) {

    fun isGranted(permission: String): Boolean =
        ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}
