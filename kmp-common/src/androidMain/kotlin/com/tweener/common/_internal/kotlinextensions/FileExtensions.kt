package com.tweener.common._internal.kotlinextensions

import android.graphics.Bitmap
import io.github.aakira.napier.Napier
import java.io.File
import java.io.FileOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Vivien Mahe
 * @since 10/10/2023
 */

suspend fun File.writeBitmap(bitmap: Bitmap, compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG) {
    withContext(Dispatchers.IO) {
        try {
            with(FileOutputStream(this@writeBitmap)) {
                bitmap.compress(compressFormat, 100, this)
                flush()
                close()
            }
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't write Bitmap file" }
        }
    }
}
