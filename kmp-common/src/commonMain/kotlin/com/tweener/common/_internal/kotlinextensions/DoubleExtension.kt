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
