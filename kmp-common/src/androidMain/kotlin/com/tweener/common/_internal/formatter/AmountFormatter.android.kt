package com.tweener.common._internal.formatter

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * @author Vivien Mahe
 * @since 20/03/2024
 */

actual fun formatAmount(value: Double, currency: String, localeLanguage: String, showDecimals: Boolean): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale(localeLanguage))
    numberFormat.currency = Currency.getInstance(currency)

    if (showDecimals.not()) numberFormat.maximumFractionDigits = 0

    return numberFormat.format(value)
}
