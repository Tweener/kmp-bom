package com.tweener.common._internal.provider

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

/**
 * @author Vivien Mahe
 * @since 19/12/2023
 */

class IOSLocaleProvider : LocaleProvider {
    override fun getLanguage(): String = NSLocale.currentLocale.languageCode
    override fun getCountry(): String = NSLocale.currentLocale.countryCode ?: LOCALE_COUNTRY_DEFAULT
}

actual fun createLocaleProvider(): LocaleProvider = IOSLocaleProvider()
