package com.tweener.common._internal.kotlinextensions

import com.tweener.common._internal.utils.Degrees
import com.tweener.common._internal.utils.Radians

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
 * Applies a percentage to this [Float].
 *
 * Example:
 * ```
 * To add 15% to 240.6: 240.6f.applyPercent(15f)
 * ```
 */
fun Float.applyPercent(percent: Float) = this + this * percent / 100
