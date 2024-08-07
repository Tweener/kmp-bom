package com.tweener.firebase.auth.provider.email

import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProvider
import com.tweener.firebase.auth.provider.FirebaseAuthProviderUnknownUserException
import com.tweener.firebase.auth.provider.FirebaseProvider
import dev.gitlive.firebase.auth.FirebaseUser
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
) : FirebaseAuthProvider<EmailAuthParams>(firebaseAuthDataSource = firebaseAuthDataSource) {

    override suspend fun signIn(params: EmailAuthParams?, onResponse: (Result<FirebaseUser>) -> Unit) {
        try {
            assertSignInParamsNotNull(params)

            firebaseAuthDataSource
                .authenticateWithEmailAndPassword(email = params.email, password = params.password)
                ?.let { firebaseUser -> onResponse(Result.success(firebaseUser)) }
                ?: onResponse(Result.failure(FirebaseAuthProviderUnknownUserException(provider = FirebaseProvider.EMAIL)))
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't sign in the user." }
            onResponse(Result.failure(throwable))
        }
    }

    /**
     * Initiates the sign-up process using email and password.
     *
     * @param params The email and password required for sign-up, encapsulated in an EmailAuthParams object.
     * @param onResponse Callback to handle the result of the sign-up process. It returns a Result object containing a FirebaseUser on success, or an exception on failure.
     */
    suspend fun signUp(params: EmailAuthParams, onResponse: (Result<FirebaseUser>) -> Unit) {
        try {
            firebaseAuthDataSource
                .createUserWithEmailAndPassword(email = params.email, password = params.password)
                ?.let { firebaseUser -> onResponse(Result.success(firebaseUser)) }
                ?: onResponse(Result.failure(FirebaseAuthProviderUnknownUserException(provider = FirebaseProvider.EMAIL)))
        } catch (throwable: Throwable) {
            onResponse(Result.failure(throwable))
        }
    }

    /**
     * Sends a password reset email with specified parameters.
     *
     * @param params The parameters required for sending the password reset email, encapsulated in a ForgotPasswordParams object.
     * @param onResponse Callback to handle the result of the password reset email process. It returns a Result object containing Unit on success, or an exception on failure.
     */
    suspend fun sendPasswordResetEmail(params: ForgotPasswordParams, onResponse: (Result<Unit>) -> Unit) {
        try {
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

            onResponse(Result.success(Unit))
        } catch (throwable: Throwable) {
            onResponse(Result.failure(throwable))
        }
    }
}
