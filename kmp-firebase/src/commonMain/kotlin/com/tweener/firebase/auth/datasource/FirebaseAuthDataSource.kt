package com.tweener.firebase.auth.datasource

import com.tweener.firebase.auth.FirebaseAuthService
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.GoogleAuthProvider

/**
 * @author Vivien Mahe
 * @since 15/01/2024
 */
class FirebaseAuthDataSource(
    private val firebaseAuthService: FirebaseAuthService,
) {

    suspend fun authenticateWithGoogleIdToken(idToken: String): FirebaseUser? {
        val firebaseCredential = GoogleAuthProvider.credential(idToken = idToken, accessToken = null)
        return firebaseAuthService.signIn(credential = firebaseCredential)
    }

    fun isUserLoggedIn(): Boolean =
        firebaseAuthService.isUserLoggedIn()

    fun getCurrentUser(): FirebaseUser? =
        firebaseAuthService.getCurrentUser()

    suspend fun signOut() {
        firebaseAuthService.signOut()
    }
}
