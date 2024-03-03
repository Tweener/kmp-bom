package com.tweener.common._internal.kotlinextensions

import android.speech.tts.TextToSpeech
import java.util.Locale

/**
 * @author Vivien Mahe
 * @since 07/09/2023
 */

fun TextToSpeech.configure(locale: Locale = Locale.getDefault()) {
    this.language = locale
}

fun TextToSpeech.terminate() {
    stop()
    shutdown()
}
