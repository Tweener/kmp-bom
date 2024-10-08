package com.tweener.common._internal.kotlinextensions

import com.tweener.common._internal.utils.Degrees
import com.tweener.common._internal.utils.Radians
import kotlin.math.pow

/**
 * @author Vivien Mahe
 * @since 05/03/2024
 */

/**
 * Returns a [Degrees] equals to this [Float] value.
 */
inline val Float.degrees: Degrees get() = Degrees(value = this.toDouble())

/**
 * Returns a [Radians] equals to this [Float] value.
 */
inline val Float.radians: Radians get() = Radians(value = this.toDouble())

/**
 * Rounds this [Float] with [decimalPrecision] as the number of decimal digits.
 */
fun Float.round(decimalPrecision: Int): Float =
    kotlin.math.round(this * 10.0f.pow(decimalPrecision)) / 10.0f.pow(decimalPrecision)

/**
 * Applies a percentage to this [Float].
 *
 * Example:
 * ```
 * To add 15% to 240.6: 240.6f.applyPercent(15f)
 * ```
 */
fun Float.applyPercent(percent: Float) = this + this * percent / 100

/**
 * Safely divides the current `Float` by the provided `Number` divisor.
 *
 * If the divisor is zero, the function returns the specified `resultIfZero`, or `0f` by default, to avoid division by zero errors.
 * Otherwise, it performs the division and returns the result.
 *
 * @param divisor The divisor as a `Number`. The function will convert this to a `Float`.
 * @param resultIfZero The value to return if the divisor is zero. Defaults to `0f`.
 * @return The result of dividing the current `Float` by the `divisor`, or the specified `resultIfZero` (default is `0f`) if the divisor is zero.
 *
 * Example usage:
 * ```
 * val result = 10.0f.safeDiv(2) // result will be 5.0f
 * val zeroDiv = 10.0f.safeDiv(0) // zeroDiv will be 0f
 * val customDiv = 10.0f.safeDiv(0, -1.0f) // customDiv will be -1.0f
 * ```
 */
fun Float.safeDiv(divisor: Number, resultIfZero: Float = 0f): Float = if (divisor.toFloat() == 0f) resultIfZero else this / divisor.toFloat()
