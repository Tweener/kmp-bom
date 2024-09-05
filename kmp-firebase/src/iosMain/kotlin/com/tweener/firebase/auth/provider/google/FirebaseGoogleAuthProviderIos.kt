package com.tweener.firebase.auth.provider.google

import cocoapods.GoogleSignIn.GIDSignIn
import com.tweener.common._internal.safeLet
import com.tweener.firebase.auth.FirebaseAuthService
import com.tweener.firebase.auth.FirebaseUser
import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProviderUnknownUserException
import com.tweener.firebase.auth.provider.FirebaseProvider
import dev.datlag.tooling.async.suspendCatching
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

    override suspend fun signIn(params: FirebaseGoogleAuthParams): Result<FirebaseUser> = suspendCatching {
        val token = retrieveIdToken().getOrThrow()
        firebaseAuthDataSource.authenticateWithGoogleIdToken(
            idToken = token.idToken,
            accessToken = token.accessToken
        ) ?: throw FirebaseAuthProviderUnknownUserException(provider = FirebaseProvider.GOOGLE)
    }

    @OptIn(ExperimentalForeignApi::class)
    private suspend fun retrieveIdToken() = suspendCoroutine<Result<GoogleTokens>> { continuation ->
        UIApplication.sharedApplication.keyWindow?.rootViewController?.let { rootViewController ->
            GIDSignIn.sharedInstance.signInWithPresentingViewController(rootViewController) { authResult, error ->
                when {
                    error != null -> continuation.resume(
                        Result.failure(
                            FirebaseGoogleAuthProviderException()
                        )
                    )

                    else -> {
                        authResult?.user?.idToken?.tokenString?.let { idToken ->
                            continuation.resume(
                                Result.success(
                                    GoogleTokens(
                                        idToken = idToken,
                                        accessToken = authResult?.user?.accessToken?.tokenString
                                    )
                                )
                            )
                        } ?: continuation.resume(
                            Result.failure(
                                FirebaseGoogleAuthProviderException()
                            )
                        )
                    }
                }
            }
        } ?: continuation.resume(Result.failure(FirebaseGoogleAuthProviderException()))
    }

    data class GoogleTokens(
        val idToken: String,
        val accessToken: String?,
    )
}
