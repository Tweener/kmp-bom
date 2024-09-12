package com.tweener.firebase.auth

import dev.datlag.sekret.Secret
import kotlin.jvm.JvmOverloads

/**
 * @author Vivien Mahe
 * @since 02/09/2024
 */
data class FirebaseUser(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val phoneNumber: String?,
    val photoUrl: String?,
    val github: GitHub?,
    val isAnonymous: Boolean,
    val isEmailVerified: Boolean,
    internal val directUser: dev.gitlive.firebase.auth.FirebaseUser
) {
    @JvmOverloads
    constructor(
        user: dev.gitlive.firebase.auth.FirebaseUser,
        githubAuthCredentials: AuthCredentials? = null
    ) : this(
        uid = user.uid,
        email = user.email?.ifBlank { null },
        displayName = user.displayName?.ifBlank { null } ?: user.providerData.map { it.displayName?.ifBlank { null } }.firstOrNull { it != null },
        phoneNumber = user.phoneNumber?.ifBlank { null } ?: user.providerData.map { it.phoneNumber?.ifBlank { null } }.firstOrNull { it != null },
        photoUrl = user.photoURL?.ifBlank { null } ?: user.providerData.map { it.photoURL?.ifBlank { null } }.firstOrNull { it != null },
        github = user.providerData.firstOrNull {
            it.providerId.equals("github", ignoreCase = true) || it.providerId.equals("github.com", ignoreCase = true)
        }?.uid?.ifBlank { null }?.let {
            GitHub(
                id = it,
                authCredentials = githubAuthCredentials
            )
        },
        isAnonymous = user.isAnonymous,
        isEmailVerified = user.isEmailVerified,
        directUser = user
    )

    data class GitHub(
        val id: String,
        val authCredentials: AuthCredentials?
    )

    data class AuthCredentials(
        @Secret val idToken: String?,
        @Secret val accessToken: String?,
        @Secret val secret: String?
    ) {
        companion object { }
    }
}
