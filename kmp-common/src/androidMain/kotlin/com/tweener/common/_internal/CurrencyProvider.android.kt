package com.tweener.common._internal

import android.icu.util.Currency
import java.util.Locale

/**
 * @author Vivien Mahe
 * @since 30/04/2024
 */

class AndroidCurrencyProvider(
    private val localeProvider: LocaleProvider,
) : CurrencyProvider {
    override fun getCurrencyCode(): String? {
        val locale = Locale(localeProvider.getLanguage(), localeProvider.getCountry())
        return Currency.getInstance(locale).currencyCode
    }
}

actual fun createCurrencyProvider(localeProvider: LocaleProvider): CurrencyProvider = AndroidCurrencyProvider(localeProvider)
