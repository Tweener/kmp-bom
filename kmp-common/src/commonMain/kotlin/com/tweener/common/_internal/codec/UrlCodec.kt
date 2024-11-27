package com.tweener.common._internal.codec

/**
 * @author Vivien Mahe
 * @since 21/11/2024
 */
class UrlCodec {

    fun decode(encodedUrl: String): String = decodeUrl(encodedUrl = encodedUrl)
}

expect fun decodeUrl(encodedUrl: String): String
