package com.tweener.firebase.auth.provider.apple

/**
 * @author Vivien Mahe
 * @since 23/08/2024
 */
class AppleAuthProviderException(message: String? = null) : Throwable("An error occurred during Apple Sign In process!${message?.let { "\n$it" }}")
