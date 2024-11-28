package com.tweener.common._internal.codec

import java.net.URLDecoder

/**
 * @author Vivien Mahe
 * @since 28/11/2024
 */

actual fun decodeUrl(encodedUrl: String): String = URLDecoder.decode(encodedUrl, "UTF-8")
