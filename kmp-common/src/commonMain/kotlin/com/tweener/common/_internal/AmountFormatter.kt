package com.tweener.common._internal

/**
 * @author Vivien Mahe
 * @since 20/03/2024
 */

expect fun formatAmount(value: Double, currency: String, localeLanguage: String, showDecimals: Boolean = true): String
