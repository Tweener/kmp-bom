package com.tweener.common._internal

/**
 * Enum class representing the different platforms.
 *
 * Currently, it includes options for Android and iOS, but more platforms might be added in the future.
 *
 * @author Vivien Mahe
 * @since 24/07/2024
 */
enum class Platform {
    ANDROID,
    IOS,
    JS,
    WASM_JS
}

/**
 * The current platform on which the code is running.
 *
 * This is an expected property that should be implemented in the respective platform-specific module.
 * It returns a value from the [Platform] enum indicating the platform on which the code is currently running.
 * Currently, it includes options for Android and iOS, but more platforms might be added in the future.
 */
expect val currentPlatform: Platform
