package com.tweener.firebase.auth.provider.google

import com.tweener.firebase.auth.FirebaseAuthService
import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import dev.gitlive.firebase.auth.FirebaseUser

/**
 * Abstract class GoogleAuthProvider for handling Google Sign-In.
 *
 * This class defines the common interface and shared functionality for platform-specific Google Sign-In implementations.
 *
 * @param serverClientId The server client ID for authenticating with Google.
 *
 * @author Vivien Mahe
 * @since 25/07/2024
 */

abstract class GoogleAuthProvider(
    protected val serverClientId: String,
) {
    protected val firebaseAuthDataSource = FirebaseAuthDataSource(firebaseAuthService = FirebaseAuthService())

    abstract suspend fun signIn(onResponse: (Result<FirebaseUser>) -> Unit)

    suspend fun signOut() {
        firebaseAuthDataSource.signOut()
    }
}
