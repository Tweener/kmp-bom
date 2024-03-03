package com.tweener.common._internal

/**
 * @author Vivien Mahe
 * @since 19/12/2023
 */

interface LocaleProvider {
    fun getLocale(): String
}

expect fun createLocaleProvider(): LocaleProvider
