package com.tweener.common._internal.codec

/**
 * @author Vivien Mahe
 * @since 21/11/2024
 */

@JsName("decodeURIComponent")
external fun jsDecodeURIComponent(encodedUrl: String): String

actual fun decodeUrl(encodedUrl: String): String {
    return try {
        jsDecodeURIComponent(encodedUrl)
    } catch (e: Throwable) {
        encodedUrl // Return the original string in case of failure
    }
}
