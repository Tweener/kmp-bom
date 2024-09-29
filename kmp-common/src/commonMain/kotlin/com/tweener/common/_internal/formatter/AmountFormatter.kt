package com.tweener.common._internal.formatter

/**
 * @author Vivien Mahe
 * @since 20/03/2024
 */

fun formatAmount(value: Double, currency: String, localeLanguage: String, showDecimals: Boolean = true, useAbbreviation: Boolean = false): String {
    // Abbreviate the number if needed
    val (abbreviatedValue, suffix) = when (useAbbreviation) {
        true -> abbreviateAmount(value = value)
        false -> Pair(value, "")
    }

    // Call the actual platform-specific implementation to format the value with the currency
    val formattedValue = getFormattedAmount(value = abbreviatedValue, currency = currency, localeLanguage = localeLanguage, showDecimals = showDecimals)

    // Insert the abbreviation at the correct position in the formatted value
    return insertAbbreviationInFormattedCurrency(formattedValue, suffix)
}

internal expect fun getFormattedAmount(value: Double, currency: String, localeLanguage: String, showDecimals: Boolean = true): String

/**
 * Returns the abbreviated amount and suffix (M, B, k).
 *
 * @param value The amount value to abbreviate
 */
private fun abbreviateAmount(value: Double): Pair<Double, String> =
    when {
        value >= 1_000_000_000 -> Pair(value / 1_000_000_000, "B")  // Billion
        value >= 1_000_000 -> Pair(value / 1_000_000, "M")  // Million
        value >= 1_000 -> Pair(value / 1_000, "k")  // Thousand
        else -> Pair(value, "")  // No abbreviation needed
    }

/**
 * Insert the abbreviation in the correct position based on the locale's currency format.
 */
private fun insertAbbreviationInFormattedCurrency(formattedCurrency: String, abbreviation: String): String =
    // Check if the currency symbol is at the start of the string or at the end
    when (formattedCurrency.first().isDigit()) {
        true -> "$formattedCurrency $abbreviation" // Currency symbol is after the number (e.g., "1.05 €"), add abbreviation before the currency symbol
        false -> {
            // Currency symbol is before the number (e.g., "$1.05"), add abbreviation after the number
            val splitIndex = formattedCurrency.indexOfFirst { it.isDigit() }
            val symbol = formattedCurrency.substring(0, splitIndex)
            val numberPart = formattedCurrency.substring(splitIndex)
            "$symbol$numberPart$abbreviation"
        }
    }
