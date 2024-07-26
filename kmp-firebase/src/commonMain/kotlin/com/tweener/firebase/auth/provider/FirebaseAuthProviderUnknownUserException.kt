package com.tweener.firebase.auth.provider

/**
 * @author Vivien Mahe
 * @since 25/07/2024
 */
class FirebaseAuthProviderUnknownUserException(provider: FirebaseProvider) : Throwable("Firebase User is null for provider: $provider")
