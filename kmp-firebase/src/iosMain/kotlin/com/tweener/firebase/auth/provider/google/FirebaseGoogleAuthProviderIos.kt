package com.tweener.firebase.auth.provider.google

import cocoapods.GoogleSignIn.GIDSignIn
import com.tweener.common._internal.safeLet
import com.tweener.firebase.auth.FirebaseAuthService
import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProviderUnknownUserException
import com.tweener.firebase.auth.provider.FirebaseProvider
import dev.gitlive.firebase.auth.FirebaseUser
import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * FirebaseGoogleAuthProviderIos class for handling Google Sign-In on iOS.
 *
 * This class extends the `FirebaseGoogleAuthProvider` to provide functionality for signing in users using their Google account
 * on an iOS device. It utilizes the GIDSignIn API to manage credentials and handle the sign-in process.
 *
 * @param firebaseAuthDataSource The data source for Firebase authentication. Defaults to a new instance of FirebaseAuthDataSource.
 * @param serverClientId The server client ID for authenticating with Google.
 *
 * @author Vivien Mahe
 * @since 25/07/2024
 */
class FirebaseGoogleAuthProviderIos(
    firebaseAuthDataSource: FirebaseAuthDataSource = FirebaseAuthDataSource(firebaseAuthService = FirebaseAuthService()),
    serverClientId: String,
) : FirebaseGoogleAuthProvider(firebaseAuthDataSource = firebaseAuthDataSource, serverClientId = serverClientId) {

    override suspend fun signIn(params: Nothing?, onResponse: (Result<FirebaseUser>) -> Unit) {
        try {
            retrieveIdToken()
                .onSuccess { tokens ->
                    firebaseAuthDataSource
                        .authenticateWithGoogleIdToken(idToken = tokens.idToken, accessToken = tokens.accessToken)
                        ?.let { firebaseUser -> onResponse(Result.success(firebaseUser)) }
                        ?: onResponse(Result.failure(FirebaseAuthProviderUnknownUserException(provider = FirebaseProvider.GOOGLE)))
                }
                .onFailure { onResponse(Result.failure(FirebaseAuthProviderUnknownUserException(provider = FirebaseProvider.GOOGLE))) }
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't sign in the user." }
            onResponse(Result.failure(throwable))
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private suspend fun retrieveIdToken() = suspendCoroutine<Result<GoogleTokens>> { continuation ->
        UIApplication.sharedApplication.keyWindow?.rootViewController
            ?.let { rootViewController ->
                GIDSignIn.sharedInstance.signInWithPresentingViewController(rootViewController) { authResult, error ->
                    error?.let { Napier.e { "Couldn't sign in with Google on iOS! $error" } }

                    when {
                        error != null -> continuation.resume(Result.failure(GoogleAuthProviderException()))

                        else -> {
                            safeLet(authResult?.user?.idToken?.tokenString, authResult?.user?.accessToken?.tokenString) { idToken, accessToken ->
                                continuation.resume(Result.success(GoogleTokens(idToken = idToken, accessToken = accessToken)))
                            } ?: continuation.resume(Result.failure(GoogleAuthProviderException()))
                        }
                    }
                }
            }
            ?: continuation.resume(Result.failure(GoogleAuthProviderException()))
    }
}

data class GoogleTokens(
    val idToken: String,
    val accessToken: String,
)
