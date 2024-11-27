package com.tweener.common._internal.codec

/**
 * @author Vivien Mahe
 * @since 21/11/2024
 */

import platform.Foundation.NSString
import platform.Foundation.stringByRemovingPercentEncoding

actual fun decodeUrl(encodedUrl: String): String =
    (encodedUrl as NSString).stringByRemovingPercentEncoding ?: encodedUrl
