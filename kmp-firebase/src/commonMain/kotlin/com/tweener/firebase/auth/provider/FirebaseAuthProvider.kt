package com.tweener.firebase.auth.provider

import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import dev.gitlive.firebase.auth.FirebaseUser

/**
 * FirebaseAuthProvider class for handling Firebase Authentication.
 *
 * This abstract class defines the common interface and shared functionality for platform-specific authentication implementations.
 *
 * @param firebaseAuthDataSource The data source for Firebase authentication.
 *
 * @author Vivien Mahe
 * @since 26/07/2024
 */
abstract class FirebaseAuthProvider(
    protected val firebaseAuthDataSource: FirebaseAuthDataSource,
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
