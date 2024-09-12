package com.tweener.firebase.auth

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.app
import dev.gitlive.firebase.auth.ActionCodeSettings
import dev.gitlive.firebase.auth.AndroidPackageName
import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.FirebaseAuth
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
class FirebaseAuthService(
    internal val app: FirebaseApp = Firebase.app
) {

    internal val auth: FirebaseAuth
        get() = Firebase.auth(app)

    /**
     * Retrieves the currently logged-in user.
     *
     * @return A Flow emitting the currently logged-in FirebaseUser, or null if no user is logged in.
     */
    val user: Flow<FirebaseUser?> = Firebase.auth(app).authStateChanged.map { it?.let(::FirebaseUser) }

    /**
     * Returns the currently signed-in Firebase user, or null if no user is signed in.
     *
     * @return The currently signed-in [FirebaseUser], or null if no user is signed in.
     */
    val currentUser: FirebaseUser?
        get() = Firebase.auth(app).currentUser?.let(::FirebaseUser)

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
    fun getCurrentUser(): FirebaseUser? = currentUser

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
    fun getCurrentUserAsFlow(): Flow<FirebaseUser?> = user

    /**
     * Signs in a user with the given authentication credentials.
     *
     * @param credential The authentication credentials used for sign-in.
     * @return The authenticated FirebaseUser, or null if authentication fails.
     */
    suspend fun signIn(credential: AuthCredential): FirebaseUser? =
        Firebase.auth(app).signInWithCredential(authCredential = credential).user?.let(::FirebaseUser)

    /**
     * Creates a new user with the given email and password.
     *
     * @param email The email address used for creating the new user.
     * @param password The password used for creating the new user.
     * @return The created FirebaseUser, or null if creation fails.
     */
    suspend fun createUserWithEmailAndPassword(email: String, password: String): FirebaseUser? =
        Firebase.auth(app).createUserWithEmailAndPassword(email = email, password = password).user?.let(::FirebaseUser)

    /**
     * Signs in a user with the given email and password.
     *
     * @param email The email address used for sign-in.
     * @param password The password used for sign-in.
     * @return The authenticated FirebaseUser, or null if authentication fails.
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser? =
        Firebase.auth(app).signInWithEmailAndPassword(email = email, password = password).user?.let(::FirebaseUser)

    /**
     * Reauthenticates the currently logged-in user with the provided credentials.
     *
     * @param credential The authentication credential used for reauthentication.
     */
    suspend fun reauthenticateUser(credential: AuthCredential) {
        Firebase.auth(app).currentUser?.reauthenticate(credential = credential)
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

        Firebase.auth(app).sendPasswordResetEmail(email = email, actionCodeSettings = actionCodeSettings)
    }

    /**
     * Signs out the currently logged-in user.
     */
    suspend fun signOut() {
        Firebase.auth(app).signOut()
    }

    /**
     * Deletes the currently logged-in user.
     *
     * Deletes the currently logged-in user from Firebase Authentication. If no user is logged in, the method does nothing.
     */
    suspend fun deleteCurrentUser() {
        Firebase.auth(app).currentUser?.delete()
    }

    /**
     * Updates the currently logged in user.
     */
    suspend fun updateCurrentUser(user: dev.gitlive.firebase.auth.FirebaseUser) {
        Firebase.auth(app).updateCurrentUser(user)
    }

    /**
     * Updates the currently logged in user.
     */
    suspend fun updateCurrentUser(user: FirebaseUser) = updateCurrentUser(user.directUser)

    suspend fun sendSignInLinkToEmail(
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

        Firebase.auth(app).sendSignInLinkToEmail(email, actionCodeSettings)
    }

    fun isSignInWithEmailLink(link: String): Boolean {
        return Firebase.auth(app).isSignInWithEmailLink(link)
    }
}
