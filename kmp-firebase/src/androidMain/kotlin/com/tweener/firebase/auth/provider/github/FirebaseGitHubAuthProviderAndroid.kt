package com.tweener.firebase.auth.provider.github

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.OAuthCredential
import com.tweener.firebase.auth.FirebaseAuthException
import com.tweener.firebase.auth.FirebaseAuthService
import com.tweener.firebase.auth.FirebaseUser
import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseProvider
import dev.datlag.tooling.async.suspendCatching
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import dev.gitlive.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.google.firebase.auth.AuthResult as AndroidAuthResult
import com.google.firebase.auth.FirebaseUser as AndroidFirebaseUser
import com.google.firebase.auth.FirebaseAuthUserCollisionException as AndroidCollisionException
import dev.gitlive.firebase.auth.android

class FirebaseGitHubAuthProviderAndroid(
    firebaseAuthDataSource: FirebaseAuthDataSource = FirebaseAuthDataSource(firebaseAuthService = FirebaseAuthService()),
    oAuthProvider: OAuthProvider
) : FirebaseGitHubAuthProvider(firebaseAuthDataSource, oAuthProvider) {

    constructor(
        firebaseAuthDataSource: FirebaseAuthDataSource,
        scopes: Collection<String>
    ) : this(
        firebaseAuthDataSource = firebaseAuthDataSource,
        oAuthProvider = OAuthProvider(
            provider = "github.com",
            scopes = scopes.toList(),
            auth = firebaseAuthDataSource.firebaseAuthService.auth
        )
    )

    constructor(
        firebaseAuthDataSource: FirebaseAuthDataSource,
        vararg scope: String
    ) : this(
        firebaseAuthDataSource = firebaseAuthDataSource,
        scopes = scope.toList()
    )

    override suspend fun signIn(params: FirebaseGitHubAuthParams): Result<FirebaseUser> = suspendCatching {
        val auth = firebaseAuthDataSource.firebaseAuthService.auth.android
        val currentUser = firebaseAuthDataSource.currentUser?.directUser?.android

        var collision: Boolean = false
        var collisionEmail: String? = null
        val linkAuthResult = suspendCatching {
            currentUser?.startActivityForLinkWithProvider(
                params,
                oAuthProvider.android
            )?.linkOrSignInUser(currentUser)?.onFailure {
                if (it is FirebaseAuthUserCollisionException || it is AndroidCollisionException) {
                    collision = true
                    it.email?.ifBlank { null }?.let { m -> collisionEmail = m }
                }
            }?.getOrNull()
        }.getOrNull()

        val authResult = linkAuthResult ?: suspendCatching {
            auth.startActivityForSignInWithProvider(
                params,
                oAuthProvider.android
            ).linkOrSignInUser(currentUser).onFailure {
                if (it is FirebaseAuthUserCollisionException || it is AndroidCollisionException) {
                    collision = true
                    it.email?.ifBlank { null }?.let { m -> collisionEmail = m }
                }
            }.getOrNull()
        }.getOrNull()

        authResult?.let {
            firebaseAuthDataSource.firebaseAuthService.auth.android.updateCurrentUser(it.first).await()
        }

        firebaseAuthDataSource.currentUser?.let {
            it.copy(
                github = it.github?.copy(
                    authCredentials = authResult?.second ?: it.github.authCredentials
                )
            )
        } ?: if (collision) {
            throw FirebaseAuthException.CollisionException(email = collisionEmail)
        } else {
            throw FirebaseAuthException.UnknownUser(provider = FirebaseProvider.GitHub)
        }
    }

    private suspend fun Task<AndroidAuthResult?>.linkOrSignInUser(existing: AndroidFirebaseUser?): Result<Pair<AndroidFirebaseUser, FirebaseUser.AuthCredentials?>> = suspendCoroutine { continuation ->
        this.addOnSuccessListener {
            val credential = it?.credential
            val authCredentials = (credential as? OAuthCredential)?.let { result ->
                FirebaseUser.AuthCredentials(result)
            }

            if (existing != null && credential != null) {
                existing.linkWithCredential(credential).addOnSuccessListener { link ->
                    continuation.resume(Result.success((link?.user ?: existing) to authCredentials))
                }.addOnFailureListener { _ ->
                    continuation.resume(Result.success((it.user ?: existing) to authCredentials))
                }
            } else {
                continuation.resume((it?.user ?: existing)?.let { u ->
                    Result.success(u to authCredentials) } ?: Result.failure(
                        FirebaseAuthException.UnknownUser(provider = FirebaseProvider.GitHub)
                    )
                )
            }
        }.addOnFailureListener {
            continuation.resume(Result.failure(it))
        }
    }

    operator fun FirebaseUser.AuthCredentials.Companion.invoke(credential: OAuthCredential): FirebaseUser.AuthCredentials? {
        val idToken = credential.idToken?.ifBlank { null }
        val accessToken = credential.accessToken?.ifBlank { null }
        val secret = credential.secret?.ifBlank { null }

        return if (idToken == null && accessToken == null && secret == null) {
            null
        } else {
            FirebaseUser.AuthCredentials(
                idToken = idToken,
                accessToken = accessToken,
                secret = secret
            )
        }
    }
}