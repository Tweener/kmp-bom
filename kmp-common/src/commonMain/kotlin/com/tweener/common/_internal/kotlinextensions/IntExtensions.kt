package com.tweener.common._internal.kotlinextensions

/**
 * @author Vivien Mahe
 * @since 29/09/2024
 */

/**
 * Applies a percentage to this [Int].
 *
 * Example:
 * ```
 * To add 15% to 240: 240.applyPercent(15f)
 * ```
 */
fun Int.applyPercent(percent: Float) = this + this * percent / 100

/**
 * Safely divides the current `Int` by the provided `Number` divisor.
 *
 * If the divisor is zero, the function returns the specified `resultIfZero`, or `0` by default, to avoid division by zero errors.
 * Otherwise, it performs the division and returns the result.
 *
 * @param divisor The divisor as a `Number`. The function will convert this to an `Int`.
 * @param resultIfZero The value to return if the divisor is zero. Defaults to `0`.
 * @return The result of dividing the current `Int` by the `divisor`, or the specified `resultIfZero` (default is `0`) if the divisor is zero.
 *
 * Example usage:
 * ```
 * val result = 10.safeDiv(2) // result will be 5
 * val zeroDiv = 10.safeDiv(0) // zeroDiv will be 0
 * val customDiv = 10.safeDiv(0, -1) // customDiv will be -1
 * ```
 */
fun Int.safeDiv(divisor: Number, resultIfZero: Int = 0): Int = if (divisor.toInt() == 0) resultIfZero else this / divisor.toInt()
