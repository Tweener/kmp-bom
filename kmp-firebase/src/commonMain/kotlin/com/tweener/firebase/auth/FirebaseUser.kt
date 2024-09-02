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
    val isAnonymous: Boolean,
    val isEmailVerified: Boolean,
)
