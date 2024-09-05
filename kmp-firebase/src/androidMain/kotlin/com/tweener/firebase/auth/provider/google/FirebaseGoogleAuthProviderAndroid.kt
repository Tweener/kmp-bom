package com.tweener.firebase.auth.provider.google

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.tweener.firebase.auth.FirebaseAuthService
import com.tweener.firebase.auth.FirebaseUser
import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProviderUnknownUserException
import com.tweener.firebase.auth.provider.FirebaseProvider
import dev.datlag.tooling.async.suspendCatching
import dev.gitlive.firebase.auth.GoogleAuthProvider
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlin.coroutines.suspendCoroutine

/**
 * FirebaseGoogleAuthProviderAndroid class for handling Google Sign-In on Android.
 *
 * This class extends the `FirebaseGoogleAuthProvider` to provide functionality for signing in users using their Google account
 * on an Android device. It utilizes the CredentialManager API to manage credentials and handle the sign-in process.
 *
 * @param firebaseAuthDataSource The data source for Firebase authentication. Defaults to a new instance of FirebaseAuthDataSource.
 * @param serverClientId The server client ID for authenticating with Google.
 * @param context The context of the calling activity or application.
 * @param filterByAuthorizedAccounts Flag indicating whether to filter by authorized accounts.
 *
 * @author Vivien Mahe
 * @since 25/07/2024
 */
class FirebaseGoogleAuthProviderAndroid(
    firebaseAuthDataSource: FirebaseAuthDataSource = FirebaseAuthDataSource(firebaseAuthService = FirebaseAuthService()),
    serverClientId: String,
    private val context: Context,
    private val filterByAuthorizedAccounts: Boolean = false,
    private val autoSelectEnabled: Boolean = true,
) : FirebaseGoogleAuthProvider(firebaseAuthDataSource = firebaseAuthDataSource, serverClientId = serverClientId) {

    private val credentialManager = CredentialManager.create(context)

    override suspend fun signIn(params: FirebaseGoogleAuthParams): Result<FirebaseUser> = suspendCatching {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts)
            .setServerClientId(serverClientId)
            .setAutoSelectEnabled(autoSelectEnabled)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = suspendCatching {
            credentialManager.getCredential(context, request)
        }.onFailure {
            // May fail sometimes with no credentials present, so try again if specified
            if (it is NoCredentialException && params.retry >= 1) {
                delay(params.delay)
                return@suspendCatching signIn(params.copy(retry = params.retry - 1)).getOrThrow()
            }
        }

        handleSignInResponse(result.getOrThrow()).getOrThrow()
    }

    private suspend fun handleSignInResponse(result: GetCredentialResponse): Result<FirebaseUser> = suspendCatching {
        when (val credential = result.credential) {
            is CustomCredential -> {
                when (credential.type) {
                    GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken

                        val linkAuthResult = suspendCatching {
                            firebaseAuthDataSource.currentUser?.directUser?.linkWithCredential(
                                credential = GoogleAuthProvider.credential(idToken = idToken, accessToken = null)
                            )
                        }.getOrNull()

                        val authResult = linkAuthResult?.user?.let(::FirebaseUser) ?: suspendCatching {
                            firebaseAuthDataSource.authenticateWithGoogleIdToken(
                                idToken = idToken
                            )
                        }.getOrNull()

                        authResult?.let {
                            firebaseAuthDataSource.updateCurrentUser(it)
                        }

                        authResult ?: firebaseAuthDataSource.currentUser ?: throw FirebaseGoogleAuthProviderUnknownCredentialException()
                    }

                    else -> throw FirebaseGoogleAuthProviderUnknownCredentialException()
                }
            }
            else -> throw FirebaseGoogleAuthProviderUnknownCredentialException()
        }
    }
}
