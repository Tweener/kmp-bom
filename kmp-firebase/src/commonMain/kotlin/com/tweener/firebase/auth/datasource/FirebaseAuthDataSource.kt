package com.tweener.firebase.auth.datasource

import com.tweener.firebase.auth.FirebaseAuthService
import dev.gitlive.firebase.auth.EmailAuthProvider
import com.tweener.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * FirebaseAuthDataSource class for handling communication with [FirebaseAuthService].
 *
 * This class provides methods to authenticate users with Google ID tokens, email and password,
 * create a new user with email and password, send a password reset email, check the authentication status of users,
 * and sign out users.
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
     * Retrieves the currently logged-in user.
     *
     * @return A Flow emitting the currently logged-in FirebaseUser, or null if no user is logged in.
     */
    val user: Flow<FirebaseUser?> = firebaseAuthService.user

    /**
     * Returns the currently signed-in Firebase user, or null if no user is signed in.
     *
     * @return The currently signed-in [FirebaseUser], or null if no user is signed in.
     */
    val currentUser: FirebaseUser?
        get() = firebaseAuthService.currentUser

    /**
     * Authenticates a user with Google credentials.
     *
     * @param idToken The Google ID token used for authentication.
     * @return The authenticated FirebaseUser, or null if authentication fails.
     */
    suspend fun authenticateWithGoogleIdToken(idToken: String, accessToken: String? = null): FirebaseUser? {
        val firebaseCredential = GoogleAuthProvider.credential(idToken = idToken, accessToken = accessToken)
        return firebaseAuthService.signIn(credential = firebaseCredential)
    }

    /**
     * Authenticates a user with an email and password.
     *
     * @param email The email address used for authentication.
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
     * Reauthenticates the currently logged-in user using Google credentials.
     *
     * @param idToken The Google ID token used for reauthentication.
     */
    suspend fun reauthenticateWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.credential(idToken = idToken, accessToken = null)
        firebaseAuthService.reauthenticateUser(credential = firebaseCredential)
    }

    /**
     * Reauthenticates the currently logged-in user using email and password credentials.
     *
     * @param email The email address used for reauthentication.
     * @param password The password used for reauthentication.
     */
    suspend fun reauthenticateWithEmailAndPassword(email: String, password: String) {
        val firebaseCredential = EmailAuthProvider.credential(email = email, password = password)
        firebaseAuthService.reauthenticateUser(credential = firebaseCredential)
    }

    /**
     * Sends a password reset email with specified settings.
     *
     * @param email The email address to send the password reset email to.
     * @param url The URL for the password reset page.
     * @param iOSBundleId The iOS bundle ID for the app. Default is null.
     * @param androidPackageName The Android package name for the app. Default is null.
     * @param installIfNotAvailable Whether to install the Android app if not available. Default is true.
     * @param minimumVersion The minimum Android version of the app required. Default is null.
     * @param canHandleCodeInApp Whether the app can handle the code in app. Default is false.
     */
    suspend fun sendPasswordResetEmail(
        email: String,
        url: String,
        iOSBundleId: String? = null,
        androidPackageName: String? = null,
        installIfNotAvailable: Boolean = true,
        minimumVersion: String? = null,
        canHandleCodeInApp: Boolean = false,
    ) {
        firebaseAuthService.sendPasswordResetEmail(
            email = email,
            url = url,
            iOSBundleId = iOSBundleId,
            androidPackageName = androidPackageName,
            installIfNotAvailable = installIfNotAvailable,
            minimumVersion = minimumVersion,
            canHandleCodeInApp = canHandleCodeInApp
        )
    }

    /**
     * Returns the currently signed-in Firebase user, or null if no user is signed in.
     *
     * @return The currently signed-in [FirebaseUser], or null if no user is signed in.
     */
    @Deprecated(
        message = "Replace with currentUser field",
        replaceWith = ReplaceWith("currentUser"),
        level = DeprecationLevel.WARNING
    )
    fun getCurrentUser(): FirebaseUser? = firebaseAuthService.currentUser

    /**
     * Retrieves the currently logged-in user.
     *
     * @return A Flow emitting the currently logged-in FirebaseUser, or null if no user is logged in.
     */
    @Deprecated(
        message = "Replace with user field",
        replaceWith = ReplaceWith("user"),
        level = DeprecationLevel.WARNING
    )
    fun getCurrentUserAsFlow(): Flow<FirebaseUser?> = firebaseAuthService.user

    /**
     * Checks if a user is currently logged in.
     *
     * @return A Flow emitting a Boolean indicating whether a user is logged in.
     */
    fun isUserLoggedIn(): Flow<Boolean> = user.map { it != null }

    /**
     * Signs out the currently logged-in user.
     */
    suspend fun signOut() {
        firebaseAuthService.signOut()
    }

    /**
     * Deletes the currently logged-in user.
     */
    suspend fun deleteCurrentUser() {
        firebaseAuthService.deleteCurrentUser()
    }

    /**
     * Updates the currently logged in user.
     */
    suspend fun updateCurrentUser(user: dev.gitlive.firebase.auth.FirebaseUser) {
        firebaseAuthService.updateCurrentUser(user)
    }

    /**
     * Updates the currently logged in user.
     */
    suspend fun updateCurrentUser(user: FirebaseUser) = updateCurrentUser(user.directUser)
}
