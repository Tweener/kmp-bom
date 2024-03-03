package com.tweener.common._internal

import java.util.Locale

/**
 * @author Vivien Mahe
 * @since 19/12/2023
 */

class AndroidLocaleProvider : LocaleProvider {
    override fun getLocale(): String = Locale.getDefault().language
}

actual fun createLocaleProvider(): LocaleProvider = AndroidLocaleProvider()
