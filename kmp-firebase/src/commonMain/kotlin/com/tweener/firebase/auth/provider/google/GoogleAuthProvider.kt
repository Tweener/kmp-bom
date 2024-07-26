package com.tweener.firebase.auth.provider.google

import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import dev.gitlive.firebase.auth.FirebaseUser

/**
 * Abstract class GoogleAuthProvider for handling Google Sign-In.
 *
 * This class defines the common interface and shared functionality for platform-specific Google Sign-In implementations.
 *
 * @param serverClientId The server client ID for authenticating with Google.
 * @param firebaseAuthDataSource The data source for Firebase authentication.
 *
 * @author Vivien Mahe
 * @since 25/07/2024
 */

abstract class GoogleAuthProvider(
    protected val serverClientId: String,
    protected val firebaseAuthDataSource: FirebaseAuthDataSource
) {
    /**
     * Abstract method to initiate the Google sign-in process.
     *
     * @param onResponse Callback to handle the result of the sign-in process. It returns a Result object containing a FirebaseUser on success, or an exception on failure.
     */
    abstract suspend fun signIn(onResponse: (Result<FirebaseUser>) -> Unit)

    /**
     * Signs out the current user.
     */
    suspend fun signOut() {
        firebaseAuthDataSource.signOut()
    }

    /**
     * Checks if a user is currently signed in.
     *
     * @return True if a user is signed in, false otherwise.
     */
    fun isUserSignedIn(): Boolean = firebaseAuthDataSource.isUserLoggedIn()

    /**
     * Retrieves the currently signed-in user.
     *
     * @return The currently signed-in FirebaseUser, or null if no user is signed in.
     */
    fun getCurrentUser(): FirebaseUser? = firebaseAuthDataSource.getCurrentUser()
}
