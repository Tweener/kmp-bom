package com.tweener.common._internal.provider

/**
 * @author Vivien Mahe
 * @since 30/04/2024
 */

class JsCurrencyProvider(
    private val localeProvider: LocaleProvider,
) : CurrencyProvider {
    override fun getCurrencyCode(): String? {
        val locale = Locale(localeProvider.getLanguage(), localeProvider.getCountry())
        return CURRENCY_DEFAULT // TODO Change this later
    }
}

actual fun createCurrencyProvider(localeProvider: LocaleProvider): CurrencyProvider = JsCurrencyProvider(localeProvider)
