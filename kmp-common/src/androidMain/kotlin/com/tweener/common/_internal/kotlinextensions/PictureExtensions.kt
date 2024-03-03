package com.tweener.common._internal.kotlinextensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Picture

/**
 * @author Vivien Mahe
 * @since 10/10/2023
 */

fun Picture.createBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.WHITE)
    canvas.drawPicture(this)

    return bitmap
}
