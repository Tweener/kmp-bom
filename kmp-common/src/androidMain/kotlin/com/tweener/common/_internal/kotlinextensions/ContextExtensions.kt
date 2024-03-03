package com.tweener.common._internal.kotlinextensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * @author Vivien Mahe
 * @since 21/11/2023
 */

/**
 * Loads a [Bitmap], matching the given [filename] from the assets directory. Returns null if couldn't load it.
 */
fun Context.loadBitmapFromAssets(filename: String): Bitmap? =
    try {
        val inputStream = assets.open(filename)
        BitmapFactory.decodeStream(inputStream)
    } catch (throwable: Throwable) {
        null
    }
