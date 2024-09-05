package com.tweener.firebase.auth.provider.apple

import com.tweener.firebase.auth.FirebaseAuthService
import com.tweener.firebase.auth.FirebaseUser
import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProviderNotImplementedException
import com.tweener.firebase.auth.provider.FirebaseAuthProviderUnknownUserException
import com.tweener.firebase.auth.provider.FirebaseProvider
import dev.datlag.tooling.async.suspendCatching

/**
 * @author Vivien Mahe
 * @since 24/08/2024
 */
class FirebaseAppleAuthProviderAndroid(
    firebaseAuthDataSource: FirebaseAuthDataSource = FirebaseAuthDataSource(firebaseAuthService = FirebaseAuthService()),
) : FirebaseAppleAuthProvider(firebaseAuthDataSource = firebaseAuthDataSource) {

    override suspend fun signIn(params: Any): Result<FirebaseUser> = suspendCatching {
        throw FirebaseAuthProviderNotImplementedException(klass = this@FirebaseAppleAuthProviderAndroid::class)

//        val auth = Firebase.auth.android
//
//        auth.pendingAuthResult
//            ?.addOnSuccessListener { handleAuthResultSuccess(onResponse = onResponse) }
//            ?.addOnFailureListener { throwable -> onResponse(Result.failure(throwable)) }
//            ?: run {
//                val oAuthProvider = OAuthProvider(provider = "apple.com", scopes = listOf("email", "name"))
//
//                auth
//                    .startActivityForSignInWithProvider(context, oAuthProvider)
//                    .addOnSuccessListener { handleAuthResultSuccess(onResponse = onResponse) }
//                    .addOnFailureListener { throwable -> onResponse(Result.failure(throwable)) }
//            }
    }

    private fun handleAuthResultSuccess(onResponse: (Result<FirebaseUser>) -> Unit) {
        firebaseAuthDataSource
            .currentUser
            ?.let { user -> onResponse(Result.success(user)) }
            ?: onResponse(Result.failure(FirebaseAuthProviderUnknownUserException(provider = FirebaseProvider.APPLE)))
    }
}
