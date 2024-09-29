package com.tweener.common._internal.kotlinextensions

/**
 * @author Vivien Mahe
 * @since 29/09/2024
 */

/**
 * Applies a percentage to this Float.
 *
 * Example:
 * ```
 * To add 15% to 240.6: 240.6f.applyPercent(15f)
 * ```
 */
fun Float.applyPercent(percent: Float) = this + this * percent / 100
