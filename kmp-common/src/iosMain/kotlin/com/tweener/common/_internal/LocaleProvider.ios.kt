package com.tweener.common._internal

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

/**
 * @author Vivien Mahe
 * @since 19/12/2023
 */

class IOSLocaleProvider : LocaleProvider {
    override fun getLocale(): String = NSLocale.currentLocale.languageCode
}

actual fun createLocaleProvider(): LocaleProvider = IOSLocaleProvider()
