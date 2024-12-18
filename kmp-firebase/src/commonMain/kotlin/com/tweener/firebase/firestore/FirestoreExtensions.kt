package com.tweener.firebase.firestore

import com.tweener.kmpkit.kotlinextensions.fromEpochMilliseconds
import com.tweener.kmpkit.kotlinextensions.toEpochMilliseconds
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.fromMilliseconds
import dev.gitlive.firebase.firestore.toMilliseconds
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * @author Vivien Mahe
 * @since 31/07/2024
 */

/**
 * Converts this LocalDate to a Firestore Timestamp.
 *
 * @return The Firestore Timestamp corresponding to this LocalDate.
 */
fun LocalDate.toTimestamp() = Timestamp.fromMilliseconds(this.toEpochMilliseconds().toDouble())

/**
 * Converts this LocalDateTime to a Firestore Timestamp.
 *
 * @return The Firestore Timestamp corresponding to this LocalDateTime.
 */
fun LocalDateTime.toTimestamp() = Timestamp.fromMilliseconds(this.toEpochMilliseconds().toDouble())

/**
 * Converts this Firestore Timestamp to a LocalDateTime.
 *
 * @return The LocalDateTime corresponding to this Firestore Timestamp.
 */
fun Timestamp.toLocalDateTime() = this.toMilliseconds().toLong().fromEpochMilliseconds()

/**
 * Converts this Firestore Timestamp to a LocalDate.
 *
 * @return The LocalDate corresponding to this Firestore Timestamp.
 */
fun Timestamp.toLocalDate() = this.toLocalDateTime().date
