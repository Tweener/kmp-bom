package com.tweener.common._internal.formatter

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle

/**
 * @author Vivien Mahe
 * @since 20/03/2024
 */

actual fun formatAmount(value: Double, currency: String, localeLanguage: String, showDecimals: Boolean): String {
    val currencyFormatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
        currencyCode = currency
        locale = NSLocale(localeLanguage)
        minimumFractionDigits = if (showDecimals) 2u else 0u
        maximumFractionDigits = if (showDecimals) 2u else 0u
    }

    return currencyFormatter.stringFromNumber(NSNumber(value)) ?: ""
}
