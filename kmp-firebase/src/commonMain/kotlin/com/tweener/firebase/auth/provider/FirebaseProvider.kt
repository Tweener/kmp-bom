package com.tweener.firebase.auth.provider

/**
 * @author Vivien Mahe
 * @since 26/07/2024
 */
sealed interface FirebaseProvider {
    data object Google : FirebaseProvider
    data object Email : FirebaseProvider
    data object Apple : FirebaseProvider
    data object GitHub : FirebaseProvider
}