package com.tweener.common._internal.kotlinextensions

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until

/**
 * @author Vivien Mahe
 * @since 05/10/2023
 */

/**
 * Returns the current LocalDateTime in the specified time zone.
 *
 * @param timeZone The time zone to use. Defaults to the system's current time zone.
 * @return The current LocalDateTime in the specified time zone.
 */
fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()) = Clock.System.now().toLocalDateTime(timeZone)

/**
 * Returns the current LocalDate in the specified time zone.
 *
 * @param timeZone The time zone to use. Defaults to the system's current time zone.
 * @return The current LocalDate in the specified time zone.
 */
fun LocalDate.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()) = LocalDateTime.now(timeZone).date

/**
 * Returns the current LocalTime in the specified time zone.
 *
 * @param timeZone The time zone to use. Defaults to the system's current time zone.
 * @return The current LocalTime in the specified time zone.
 */
fun LocalTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()) = LocalDateTime.now(timeZone).time

/**
 * Converts this LocalDate to a LocalDateTime at the current time in the specified time zone.
 *
 * @param timeZone The time zone to use. Defaults to the system's current time zone.
 * @return The LocalDateTime corresponding to this LocalDate at the current time.
 */
fun LocalDate.toLocalDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()) = this.atTime(time = LocalTime.now(timeZone))

/**
 * Checks if this LocalDate is after the specified LocalDate.
 *
 * @param other The other LocalDate to compare to.
 * @return True if this LocalDate is after the specified LocalDate, false otherwise.
 */
fun LocalDate.isAfter(other: LocalDate) = this > other

/**
 * Checks if this LocalDate is before the specified LocalDate.
 *
 * @param other The other LocalDate to compare to.
 * @return True if this LocalDate is before the specified LocalDate, false otherwise.
 */
fun LocalDate.isBefore(other: LocalDate) = this < other

/**
 * Checks if this LocalDate is the same as the specified LocalDate.
 *
 * @param other The other LocalDate to compare to.
 * @return True if this LocalDate is the same as the specified LocalDate, false otherwise.
 */
fun LocalDate.isSameDate(other: LocalDate) = this == other

/**
 * Converts this LocalDate to the number of milliseconds since the epoch at the current time in the specified time zone.
 *
 * @param timeZone The time zone to use. Defaults to the system's current time zone.
 * @return The number of milliseconds since the epoch for this LocalDate at the current time.
 */
fun LocalDate.toEpochMilliseconds(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    val timeNow = LocalDateTime.now(timeZone = timeZone)
    return this.atTime(hour = timeNow.hour, minute = timeNow.minute).toInstant(timeZone = timeZone).toEpochMilliseconds()
}

/**
 * Converts this LocalDateTime to the number of milliseconds since the epoch in the specified time zone.
 *
 * @param timeZone The time zone to use. Defaults to the system's current time zone.
 * @return The number of milliseconds since the epoch for this LocalDateTime.
 */
fun LocalDateTime.toEpochMilliseconds(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return this.toInstant(timeZone = timeZone).toEpochMilliseconds()
}

/**
 * Converts the given number of milliseconds since the epoch to a LocalDateTime in the specified time zone.
 *
 * @param timeZone The time zone to use. Defaults to the system's current time zone.
 * @return The LocalDateTime corresponding to the given number of milliseconds since the epoch.
 */
fun Long.fromEpochMilliseconds(timeZone: TimeZone = TimeZone.currentSystemDefault()) = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)

/**
 * Returns the number of days in the current month for this LocalDateTime.
 *
 * @return The number of days in the month.
 */
fun LocalDateTime.daysInMonth(): Int = date.daysInMonth()

/**
 * Returns the number of days in the current month for this LocalDate.
 *
 * @return The number of days in the month.
 */
fun LocalDate.daysInMonth(): Int {
    val start = LocalDate(year, month, 1)
    val end = start.plus(1, DateTimeUnit.MONTH)
    return start.until(end, DateTimeUnit.DAY)
}
