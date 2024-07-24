package com.tweener.common._internal

/**
 * @author Vivien Mahe
 * @since 24/07/2024
 */
enum class Platform {
    ANDROID,
    IOS
}

expect val currentPlatform: Platform
