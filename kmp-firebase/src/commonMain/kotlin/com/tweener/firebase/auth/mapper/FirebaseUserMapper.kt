package com.tweener.firebase.auth.mapper

import com.tweener.firebase.auth.FirebaseUser

/**
 * @author Vivien Mahe
 * @since 02/09/2024
 */

/**
 * Convert a GitLive FirebaseUser to a Tweener FirebaseUser, by pulling data from all user providers.
 */
fun dev.gitlive.firebase.auth.FirebaseUser.toTweenerFirebaseUser(): FirebaseUser =
    FirebaseUser(
        uid = uid,
        email = email,
        displayName = displayName ?: providerData.map { it.displayName }.firstOrNull { it != null },
        phoneNumber = phoneNumber ?: providerData.map { it.phoneNumber }.firstOrNull { it != null },
        photoUrl = photoURL ?: providerData.map { it.photoURL }.firstOrNull { it != null },
        isAnonymous = isAnonymous,
        isEmailVerified = isEmailVerified,
    )
