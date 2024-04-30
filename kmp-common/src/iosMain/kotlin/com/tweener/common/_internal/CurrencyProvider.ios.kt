package com.tweener.common._internal

import platform.Foundation.NSLocale
import platform.Foundation.currencyCode
import platform.Foundation.localeWithLocaleIdentifier

/**
 * @author Vivien Mahe
 * @since 30/04/2024
 */

class IOSCurrencyProvider(
    private val localeProvider: LocaleProvider,
) : CurrencyProvider {
    override fun getCurrencyCode(): String = NSLocale.localeWithLocaleIdentifier(localeProvider.getLanguage()).currencyCode ?: "USD"
}

actual fun createCurrencyProvider(localeProvider: LocaleProvider): CurrencyProvider = IOSCurrencyProvider(localeProvider)
