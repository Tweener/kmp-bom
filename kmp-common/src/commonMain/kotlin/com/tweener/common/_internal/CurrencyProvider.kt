package com.tweener.common._internal

/**
 * @author Vivien Mahe
 * @since 30/04/2024
 */
interface CurrencyProvider {
    fun getCurrencyCode(): String?
}

expect fun createCurrencyProvider(localeProvider: LocaleProvider): CurrencyProvider
