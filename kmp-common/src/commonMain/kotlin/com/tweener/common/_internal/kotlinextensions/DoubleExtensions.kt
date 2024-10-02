package com.tweener.common._internal.kotlinextensions

import com.tweener.common._internal.utils.Degrees
import com.tweener.common._internal.utils.Radians
import kotlin.math.pow

/**
 * @author Vivien Mahe
 * @since 05/03/2024
 */

/**
 * Returns a [Degrees] equals to this [Double] value.
 */
inline val Double.degrees: Degrees get() = Degrees(value = this)

/**
 * Returns a [Radians] equals to this [Double] value.
 */
inline val Double.radians: Radians get() = Radians(value = this)

/**
 * Rounds this [Double] with [decimalPrecision] as the number of decimal digits.
 */
fun Double.round(decimalPrecision: Int): Double =
    kotlin.math.round(this * 10.0.pow(decimalPrecision)) / 10.0.pow(decimalPrecision)

/**
 * Applies a percentage to this [Double].
 *
 * Example:
 * ```
 * To add 15% to 240.6: 240.6.applyPercent(15f)
 * ```
 */
fun Double.applyPercent(percent: Float) = this + this * percent / 100

/**
 * Safely divides the current `Double` by the provided `Number` divisor.
 *
 * If the divisor is zero, the function returns the specified `resultIfZero`, or `0.0` by default, to avoid division by zero errors.
 * Otherwise, it performs the division and returns the result.
 *
 * @param divisor The divisor as a `Number`. The function will convert this to a `Double`.
 * @param resultIfZero The value to return if the divisor is zero. Defaults to `0.0`.
 * @return The result of dividing the current `Double` by the `divisor`, or the specified `resultIfZero` (default is `0.0`) if the divisor is zero.
 *
 * Example usage:
 * ```
 * val result = 10.0.safeDiv(2) // result will be 5.0
 * val zeroDiv = 10.0.safeDiv(0) // zeroDiv will be 0.0
 * val customDiv = 10.0.safeDiv(0, -1.0) // customDiv will be -1.0
 * ```
 */
fun Double.safeDiv(divisor: Number, resultIfZero: Double = 0.0): Double = if (divisor.toDouble() == 0.0) resultIfZero else this / divisor.toDouble()
