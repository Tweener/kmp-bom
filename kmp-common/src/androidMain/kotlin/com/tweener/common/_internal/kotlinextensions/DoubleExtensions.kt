package com.tweener.common._internal.kotlinextensions

/**
 * @author Vivien Mahe
 * @since 29/09/2024
 */

/**
 * Applies a percentage to this Double.
 *
 * Example:
 * ```
 * To add 15% to 240.6: 240.6.applyPercent(15f)
 * ```
 */
fun Double.applyPercent(percent: Float) = this + this * percent / 100
