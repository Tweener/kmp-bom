package com.tweener.firebase.auth

import com.tweener.firebase.auth.provider.FirebaseProvider

sealed interface FirebaseAuthException {

    data class NotImplemented(val provider: FirebaseProvider) : UnsupportedOperationException(
        "Firebase Authentication is not yet implemented for provider: $provider"
    )

    data class UnknownUser(val provider: FirebaseProvider) : IllegalStateException("Firebase User is null for provider: $provider")

    data class CollisionException(
        val email: String?
    ) : Exception("Logging in with $email requires to use another provider")

    sealed interface Google : FirebaseAuthException {

        data object Unknown : IllegalStateException("Unknown error occurred during Google Sign In")

        data object UnknownCredential : IllegalArgumentException("Unexpected type of credential")
    }
}