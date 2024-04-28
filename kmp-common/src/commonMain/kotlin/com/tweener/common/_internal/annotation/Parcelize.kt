package com.tweener.common._internal.annotation

/**
 * @author Vivien Mahe
 * @since 08/04/2024
 */
@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
expect annotation class Parcelize()

expect interface Parcelable
