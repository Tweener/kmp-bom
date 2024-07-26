package com.tweener.firebase.auth.provider.google

import cocoapods.GoogleSignIn.GIDSignIn
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * GoogleAuthProviderIos class for handling Google Sign-In on iOS.
 *
 * This class extends the `GoogleAuthProvider` to provide functionality for signing in users using their Google account
 * on an iOS device. It utilizes the GIDSignIn API to manage credentials and handle the sign-in process.
 *
 * @param serverClientId The server client ID for authenticating with Google.
 *
 * @author Vivien Mahe
 * @since 25/07/2024
 */
class GoogleAuthProviderIos(
    serverClientId: String,
) : GoogleAuthProvider(serverClientId) {

    override suspend fun signIn(onResponse: (Result<FirebaseUser>) -> Unit) {
        retrieveIdToken()
            .getOrNull()
            ?.let { idToken ->
                firebaseAuthDataSource
                    .authenticateWithGoogleIdToken(idToken = idToken)
                    ?.let { firebaseUser -> onResponse(Result.success(firebaseUser)) }
                    ?: onResponse(Result.failure(GoogleAuthProviderUnknownUserException()))
            }
            ?: onResponse(Result.failure(GoogleAuthProviderUnknownUserException()))
    }

    @OptIn(ExperimentalForeignApi::class)
    private suspend fun retrieveIdToken() = suspendCoroutine<Result<String?>> { continuation ->
        UIApplication.sharedApplication.keyWindow?.rootViewController
            ?.let { rootViewController ->
                GIDSignIn.sharedInstance.signInWithPresentingViewController(rootViewController) { gidSignInResult, nsError ->
                    when {
                        nsError != null -> continuation.resume(Result.failure(GoogleAuthProviderException()))

                        else -> {
                            gidSignInResult?.user?.idToken?.tokenString
                                ?.let { idToken -> continuation.resume(Result.success(idToken)) }
                                ?: continuation.resume(Result.failure(GoogleAuthProviderException()))
                        }
                    }
                }
            }
    }
}
