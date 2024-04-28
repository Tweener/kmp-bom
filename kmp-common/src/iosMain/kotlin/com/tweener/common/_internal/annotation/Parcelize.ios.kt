package com.tweener.common._internal.annotation

/**
 * @author Vivien Mahe
 * @since 08/04/2024
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
actual annotation class Parcelize actual constructor()

actual interface Parcelable
