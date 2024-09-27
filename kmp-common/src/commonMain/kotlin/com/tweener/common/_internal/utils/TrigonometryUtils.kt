package com.tweener.common._internal.utils

import kotlin.jvm.JvmInline
import kotlin.math.PI

/**
 * @author Vivien Mahe
 * @since 05/03/2024
 */

@JvmInline
value class Degrees(val value: Double) : Comparable<Degrees> {

    operator fun plus(other: Degrees) = Degrees(value = this.value + other.value)

    operator fun plus(other: Int) = Degrees(value = this.value + other)

    operator fun plus(other: Float) = Degrees(value = this.value + other)

    operator fun plus(other: Double) = Degrees(value = this.value + other)

    operator fun minus(other: Degrees) = Degrees(value = this.value - other.value)

    operator fun minus(other: Int) = Degrees(value = this.value - other)

    operator fun minus(other: Float) = Degrees(value = this.value - other)

    operator fun minus(other: Double) = Degrees(value = this.value - other)

    operator fun div(other: Degrees): Degrees = Degrees(value = value / other.value)

    operator fun div(other: Int): Degrees = Degrees(value = value / other)

    operator fun div(other: Float): Degrees = Degrees(value = value / other)

    operator fun div(other: Double): Degrees = Degrees(value = value / other)

    operator fun times(other: Degrees): Degrees = Degrees(value = value * other.value)

    operator fun times(other: Int): Degrees = Degrees(value = value * other)

    operator fun times(other: Float): Degrees = Degrees(value = value * other)

    operator fun times(other: Double): Degrees = Degrees(value = value * other)

    override fun compareTo(other: Degrees): Int = value.compareTo(other.value)

    operator fun compareTo(other: Int): Int = value.compareTo(other)

    operator fun compareTo(other: Float): Int = value.compareTo(other)

    operator fun compareTo(other: Double): Int = value.compareTo(other)

    /**
     * Converts this [Degrees] to a [Radians].
     */
    fun toRadians(): Radians = Radians(this.value * PI / 180.0)
}

@JvmInline
value class Radians(val value: Double) : Comparable<Radians> {

    operator fun plus(other: Radians) = Radians(value = this.value + other.value)

    operator fun plus(other: Int) = Radians(value = this.value + other)

    operator fun plus(other: Float) = Radians(value = this.value + other)

    operator fun plus(other: Double) = Radians(value = this.value + other)

    operator fun minus(other: Radians) = Radians(value = this.value - other.value)

    operator fun minus(other: Int) = Radians(value = this.value + other)

    operator fun minus(other: Float) = Radians(value = this.value + other)

    operator fun minus(other: Double) = Radians(value = this.value + other)

    operator fun div(other: Radians): Radians = Radians(value = value / other.value)

    operator fun div(other: Int) = Radians(value = this.value + other)

    operator fun div(other: Float) = Radians(value = this.value + other)

    operator fun div(other: Double): Radians = Radians(value = value / other)

    operator fun times(other: Radians): Radians = Radians(value = value * other.value)

    operator fun times(other: Int) = Radians(value = this.value + other)

    operator fun times(other: Float) = Radians(value = this.value + other)

    operator fun times(other: Double): Radians = Radians(value = value * other)

    override fun compareTo(other: Radians): Int = value.compareTo(other.value)

    operator fun compareTo(other: Int): Int = value.compareTo(other)

    operator fun compareTo(other: Float): Int = value.compareTo(other)

    operator fun compareTo(other: Double): Int = value.compareTo(other)

    /**
     * Converts this [Radians] to a [Degrees].
     */
    fun toDegrees(): Degrees = Degrees(this.value * 180.0 / PI)
}
