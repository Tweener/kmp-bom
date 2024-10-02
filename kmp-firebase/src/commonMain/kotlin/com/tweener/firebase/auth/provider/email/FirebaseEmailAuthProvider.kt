package com.tweener.firebase.auth.provider.email

import com.tweener.common._internal.thread.suspendCatching
import com.tweener.firebase.auth.FirebaseUser
import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProvider
import com.tweener.firebase.auth.provider.FirebaseAuthProviderUnknownUserException
import com.tweener.firebase.auth.provider.FirebaseProvider
import io.github.aakira.napier.Napier

/**
 * FirebaseEmailAuthProvider class for handling authentication with Firebase via email.
 *
 *
 * This class extends the `FirebaseAuthProvider` to provide functionality for signing in users using their email and password.
 * It also provides methods to create a new user with email and password and send a password reset email.
 *
 * @param firebaseAuthDataSource The data source for Firebase authentication.
 *
 * @author Vivien Mahe
 * @since 26/07/2024
 */
class FirebaseEmailAuthProvider(
    firebaseAuthDataSource: FirebaseAuthDataSource,
) : FirebaseAuthProvider<FirebaseEmailAuthParams>(firebaseAuthDataSource = firebaseAuthDataSource) {

    override suspend fun signIn(params: FirebaseEmailAuthParams?): Result<FirebaseUser> = suspendCatching {
        assertSignInParamsNotNull(params)

        firebaseAuthDataSource
            .authenticateWithEmailAndPassword(email = params.email, password = params.password)
            ?: throw FirebaseAuthProviderUnknownUserException(provider = FirebaseProvider.EMAIL)
    }.onFailure { throwable ->
        Napier.e(throwable) { "Couldn't sign in the user." }
    }

    /**
     * Initiates the sign-up process using email and password.
     *
     * @param params The email and password required for sign-up, encapsulated in an EmailAuthParams object.
     */
    suspend fun signUp(params: FirebaseEmailAuthParams): Result<FirebaseUser> = suspendCatching {
        firebaseAuthDataSource
            .createUserWithEmailAndPassword(email = params.email, password = params.password)
            ?: throw FirebaseAuthProviderUnknownUserException(provider = FirebaseProvider.EMAIL)
    }.onFailure { throwable ->
        Napier.e(throwable) { "Couldn't sign up the user." }
    }

    /**
     * Sends a password reset email with specified parameters.
     *
     * @param params The parameters required for sending the password reset email, encapsulated in a ForgotPasswordParams object.
     */
    suspend fun sendPasswordResetEmail(params: ForgotPasswordParams): Result<Unit> = suspendCatching {
        firebaseAuthDataSource
            .sendPasswordResetEmail(
                email = params.email,
                url = params.url,
                iOSBundleId = params.iosParams?.iOSBundleId,
                androidPackageName = params.androidParams?.androidPackageName,
                installIfNotAvailable = params.androidParams?.installIfNotAvailable ?: true,
                minimumVersion = params.androidParams?.minimumVersion,
                canHandleCodeInApp = params.canHandleCodeInApp,
            )
    }.onFailure { throwable ->
        Napier.e(throwable) { "Couldn't send reset password email." }
    }
}
