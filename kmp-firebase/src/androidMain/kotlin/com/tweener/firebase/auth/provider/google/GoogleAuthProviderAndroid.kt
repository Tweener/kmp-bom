package com.tweener.firebase.auth.provider.google

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dev.gitlive.firebase.auth.FirebaseUser
import io.github.aakira.napier.Napier

/**
 * GoogleAuthProviderAndroid class for handling Google Sign-In on Android.
 *
 * This class extends the `GoogleAuthProvider` to provide functionality for signing in users using their Google account
 * on an Android device. It utilizes the CredentialManager API to manage credentials and handle the sign-in process.
 *
 * @param serverClientId The server client ID for authenticating with Google.
 * @param context The context of the calling activity or application.
 * @param filterByAuthorizedAccounts Flag indicating whether to filter by authorized accounts.
 *
 * @author Vivien Mahe
 * @since 25/07/2024
 */
class GoogleAuthProviderAndroid(
    serverClientId: String,
    private val context: Context,
    private val filterByAuthorizedAccounts: Boolean = false,
) : GoogleAuthProvider(serverClientId) {

    private val credentialManager = CredentialManager.create(context)

    override suspend fun signIn(onResponse: (Result<FirebaseUser>) -> Unit) {
        try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts)
                .setServerClientId(serverClientId)
                .setAutoSelectEnabled(true)
                .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(request = request, context = context)

            handleSignInResponse(result, onResponse)
        } catch (throwable: GetCredentialException) {
            Napier.e(throwable) { "Couldn't sign in the user." }
            onResponse(Result.failure(throwable))
        }
    }

    private suspend fun handleSignInResponse(result: GetCredentialResponse, onResponse: (Result<FirebaseUser>) -> Unit) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                when (credential.type) {
                    GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                            val idToken = googleIdTokenCredential.idToken
                            Napier.d { "Successful Google Sin In flow with idToken: $idToken" }

                            firebaseAuthDataSource
                                .authenticateWithGoogleIdToken(idToken = idToken)
                                ?.let { firebaseUser -> onResponse(Result.success(firebaseUser)) }
                                ?: onResponse(Result.failure(GoogleAuthProviderUnknownUserException()))

                        } catch (throwable: GoogleIdTokenParsingException) {
                            Napier.e(throwable) { "Received an invalid google id token response." }
                            onResponse(Result.failure(throwable))
                        }
                    }

                    else -> {
                        Napier.d { "Unexpected type of credential" }
                        onResponse(Result.failure(GoogleAuthProviderUnknownCredentialException()))
                    }
                }
            }

            else -> {
                Napier.d { "Unexpected type of credential" }
                onResponse(Result.failure(GoogleAuthProviderUnknownCredentialException()))
            }
        }
    }
}
