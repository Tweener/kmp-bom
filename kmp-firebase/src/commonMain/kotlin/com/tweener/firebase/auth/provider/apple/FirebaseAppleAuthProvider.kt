package com.tweener.firebase.auth.provider.apple

import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProvider

/**
 * @author Vivien Mahe
 * @since 23/08/2024
 */
abstract class FirebaseAppleAuthProvider(
    firebaseAuthDataSource: FirebaseAuthDataSource,
) : FirebaseAuthProvider<Nothing>(firebaseAuthDataSource = firebaseAuthDataSource)
