package com.tweener.common._internal.kotlinextensions

/**
 * @author Vivien Mahe
 * @since 29/09/2024
 */

/**
 * Applies a percentage to this Integer.
 *
 * Example:
 * ```
 * To add 15% to 240: 240.applyPercent(15f)
 * ```
 */
fun Int.applyPercent(percent: Float) = this + this * percent / 100
