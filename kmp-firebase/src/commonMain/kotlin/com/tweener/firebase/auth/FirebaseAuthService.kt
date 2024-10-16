package com.tweener.firebase.auth

import com.tweener.firebase.auth.mapper.toTweenerFirebaseUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.ActionCodeSettings
import dev.gitlive.firebase.auth.AndroidPackageName
import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * FirebaseAuthService class for handling Firebase authentication operations.
 *
 * This class provides methods to get the current user, check if a user is logged in,
 * sign in with credentials, sign in with email and password, create a new user with email and password,
 * send a password reset email, and sign out users.
 *
 * @author Vivien Mahe
 * @since 15/01/2024
 */
class FirebaseAuthService {

    /**
     * Returns the currently signed-in Firebase user, or null if no user is signed in.
     *
     * @return The currently signed-in [FirebaseUser], or null if no user is signed in.
     */
    fun getCurrentUser(): FirebaseUser? =
        Firebase.auth.currentUser?.toTweenerFirebaseUser()

    /**
     * Retrieves the currently logged-in user.
     *
     * @return A Flow emitting the currently logged-in FirebaseUser, or null if no user is logged in.
     */
    fun getCurrentUserAsFlow(): Flow<FirebaseUser?> =
        Firebase.auth.authStateChanged.map { it?.toTweenerFirebaseUser() }

    /**
     * Signs in a user with the given authentication credentials.
     *
     * @param credential The authentication credentials used for sign-in.
     * @return The authenticated FirebaseUser, or null if authentication fails.
     */
    suspend fun signIn(credential: AuthCredential): FirebaseUser? =
        Firebase.auth.signInWithCredential(authCredential = credential).user?.toTweenerFirebaseUser()

    /**
     * Creates a new user with the given email and password.
     *
     * @param email The email address used for creating the new user.
     * @param password The password used for creating the new user.
     * @return The created FirebaseUser, or null if creation fails.
     */
    suspend fun createUserWithEmailAndPassword(email: String, password: String): FirebaseUser? =
        Firebase.auth.createUserWithEmailAndPassword(email = email, password = password).user?.toTweenerFirebaseUser()

    /**
     * Signs in a user with the given email and password.
     *
     * @param email The email address used for sign-in.
     * @param password The password used for sign-in.
     * @return The authenticated FirebaseUser, or null if authentication fails.
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser? =
        Firebase.auth.signInWithEmailAndPassword(email = email, password = password).user?.toTweenerFirebaseUser()

    /**
     * Reauthenticates the currently logged-in user with the provided credentials.
     *
     * @param credential The authentication credential used for reauthentication.
     */
    suspend fun reauthenticateUser(credential: AuthCredential) {
        Firebase.auth.currentUser?.reauthenticate(credential = credential)
    }

    /**
     * Sends a password reset email with specified settings.
     *
     * @param email The email address to send the password reset email to.
     * @param url The URL for the password reset page.
     * @param iOSBundleId The iOS bundle ID for the app.
     * @param androidPackageName The Android package name for the app.
     * @param installIfNotAvailable Whether to install the Android app if not available.
     * @param minimumVersion The minimum Android version of the app required.
     * @param canHandleCodeInApp Whether the app can handle the code in app.
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
        val actionCodeSettings = ActionCodeSettings(
            url = url,
            androidPackageName = androidPackageName?.let { AndroidPackageName(packageName = it, installIfNotAvailable = installIfNotAvailable, minimumVersion = minimumVersion) },
            iOSBundleId = iOSBundleId,
            canHandleCodeInApp = canHandleCodeInApp,
        )

        Firebase.auth.sendPasswordResetEmail(email = email, actionCodeSettings = actionCodeSettings)
    }

    /**
     * Signs out the currently logged-in user.
     */
    suspend fun signOut() {
        Firebase.auth.signOut()
    }

    /**
     * Deletes the currently logged-in user.
     *
     * Deletes the currently logged-in user from Firebase Authentication. If no user is logged in, the method does nothing.
     */
    suspend fun deleteCurrentUser() {
        Firebase.auth.currentUser?.delete()
    }
}
