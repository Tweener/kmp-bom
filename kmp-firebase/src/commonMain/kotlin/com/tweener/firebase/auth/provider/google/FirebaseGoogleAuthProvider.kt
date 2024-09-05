package com.tweener.firebase.auth.provider.google

import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProvider

/**
 * FirebaseGoogleAuthProvider class for handling Google Sign-In.
 *
 * This class extends the `FirebaseAuthProvider` to provide functionality for signing in users using their Google account.
 *
 * @param firebaseAuthDataSource The data source for Firebase authentication.
 * @param serverClientId The server client ID for authenticating with Google.
 *
 * @author Vivien Mahe
 * @since 25/07/2024
 */
abstract class FirebaseGoogleAuthProvider(
    firebaseAuthDataSource: FirebaseAuthDataSource,
    protected val serverClientId: String,
) : FirebaseAuthProvider<FirebaseGoogleAuthParams>(firebaseAuthDataSource = firebaseAuthDataSource)
