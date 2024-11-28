package com.tweener.common._internal.provider

import java.util.Locale

/**
 * @author Vivien Mahe
 * @since 28/11/2024
 */

class JvmLocaleProvider : LocaleProvider {
    override fun getLanguage(): String = Locale.getDefault().language
    override fun getCountry(): String = Locale.getDefault().country
}

actual fun createLocaleProvider(): LocaleProvider = JvmLocaleProvider()
