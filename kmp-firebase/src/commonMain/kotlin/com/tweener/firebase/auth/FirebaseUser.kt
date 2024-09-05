package com.tweener.firebase.auth

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
    val githubId: String?,
    val isAnonymous: Boolean,
    val isEmailVerified: Boolean,
    val directUser: dev.gitlive.firebase.auth.FirebaseUser
) {
    constructor(user: dev.gitlive.firebase.auth.FirebaseUser) : this(
        uid = user.uid,
        email = user.email?.ifBlank { null },
        displayName = user.displayName?.ifBlank { null } ?: user.providerData.map { it.displayName?.ifBlank { null } }.firstOrNull { it != null },
        phoneNumber = user.phoneNumber?.ifBlank { null } ?: user.providerData.map { it.phoneNumber?.ifBlank { null } }.firstOrNull { it != null },
        photoUrl = user.photoURL?.ifBlank { null } ?: user.providerData.map { it.photoURL?.ifBlank { null } }.firstOrNull { it != null },
        githubId = user.providerData.firstOrNull {
            it.providerId.equals("github", ignoreCase = true) || it.providerId.equals("github.com", ignoreCase = true)
        }?.uid?.ifBlank { null },
        isAnonymous = user.isAnonymous,
        isEmailVerified = user.isEmailVerified,
        directUser = user
    )
}
