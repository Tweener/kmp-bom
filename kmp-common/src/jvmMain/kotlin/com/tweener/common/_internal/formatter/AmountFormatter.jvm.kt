package com.tweener.common._internal.formatter

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * @author Vivien Mahe
 * @since 28/11/2024
 */

internal actual fun getFormattedAmount(value: Double, currency: String, localeLanguage: String, showDecimals: Boolean): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.of(localeLanguage)).apply {
        this.currency = Currency.getInstance(currency)
        if (showDecimals.not()) maximumFractionDigits = 0
        if (showDecimals.not()) minimumFractionDigits = 0
    }

    return numberFormat.format(value)
}
