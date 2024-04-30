package com.tweener.common._internal

import java.util.Locale

/**
 * @author Vivien Mahe
 * @since 19/12/2023
 */

class AndroidLocaleProvider : LocaleProvider {
    override fun getLanguage(): String = Locale.getDefault().language
    override fun getCountry(): String = Locale.getDefault().country
}

actual fun createLocaleProvider(): LocaleProvider = AndroidLocaleProvider()
