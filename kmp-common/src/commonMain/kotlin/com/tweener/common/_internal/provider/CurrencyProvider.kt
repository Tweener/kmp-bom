package com.tweener.common._internal.provider

/**
 * @author Vivien Mahe
 * @since 30/04/2024
 */

const val CURRENCY_DEFAULT = "USD"

interface CurrencyProvider {
    fun getCurrencyCode(): String?
}

expect fun createCurrencyProvider(localeProvider: LocaleProvider): CurrencyProvider
