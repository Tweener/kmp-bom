package com.tweener.firebase.auth.datasource

import com.tweener.firebase.auth.FirebaseAuthService
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.GoogleAuthProvider

/**
 * FirebaseAuthDataSource class for handling communication with [FirebaseAuthService].
 *
 * This class provides methods to authenticate users with Google ID tokens, email and password, and to check the authentication status of users. It also allows signing out users.
 *
 * @param firebaseAuthService The service used for Firebase authentication operations.
 *
 * @author Vivien Mahe
 * @since 15/01/2024
 */
class FirebaseAuthDataSource(
    private val firebaseAuthService: FirebaseAuthService,
) {

    /**
     * Authenticates a user with a Google ID token.
     *
     * @param idToken The Google ID token used for authentication.
     * @return The authenticated FirebaseUser, or null if authentication fails.
     */
    suspend fun authenticateWithGoogleIdToken(idToken: String): FirebaseUser? {
        val firebaseCredential = GoogleAuthProvider.credential(idToken = idToken, accessToken = null)
        return firebaseAuthService.signIn(credential = firebaseCredential)
    }

    /**
     * Authenticates a user with an email and password.
     *
     * @param email The email used for authentication.
     * @param password The password used for authentication.
     * @return The authenticated FirebaseUser, or null if authentication fails.
     */
    suspend fun authenticateWithEmailAndPassword(email: String, password: String): FirebaseUser? =
        firebaseAuthService.signInWithEmailAndPassword(email = email, password = password)

    /**
     * Creates a new user with the given email and password.
     *
     * @param email The email address used for creating the new user.
     * @param password The password used for creating the new user.
     * @return The created FirebaseUser, or null if creation fails.
     */
    suspend fun createUserWithEmailAndPassword(email: String, password: String): FirebaseUser? =
        firebaseAuthService.createUserWithEmailAndPassword(email = email, password = password)

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    fun isUserLoggedIn(): Boolean =
        firebaseAuthService.isUserLoggedIn()

    /**
     * Retrieves the currently logged-in user.
     *
     * @return The currently logged-in FirebaseUser, or null if no user is logged in.
     */
    fun getCurrentUser(): FirebaseUser? =
        firebaseAuthService.getCurrentUser()

    /**
     * Signs out the currently logged-in user.
     */
    suspend fun signOut() {
        firebaseAuthService.signOut()
    }
}
