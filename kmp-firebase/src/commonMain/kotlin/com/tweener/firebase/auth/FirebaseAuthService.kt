package com.tweener.firebase.auth

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import io.github.aakira.napier.Napier

/**
 * @author Vivien Mahe
 * @since 15/01/2024
 */
class FirebaseAuthService {

    fun getCurrentUser(): FirebaseUser? {
        return try {
            Firebase.auth.currentUser
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't set Firebase auth" }
            null
        }
    }

    fun isUserLoggedIn() = getCurrentUser() != null

    suspend fun signIn(credential: AuthCredential): FirebaseUser? =
        Firebase.auth.signInWithCredential(authCredential = credential).user

    suspend fun signOut() {
        Firebase.auth.signOut()
    }
}
