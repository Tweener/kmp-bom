package com.tweener.common._internal.kotlinextensions

/**
 * @author Vivien Mahe
 * @since 19/11/2023
 */

fun <K, V> MutableMap<K, V>.replace(elements: Map<K, V>) = with(this) { clear(); putAll(elements) }
