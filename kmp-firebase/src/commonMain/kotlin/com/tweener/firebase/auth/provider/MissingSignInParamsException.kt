package com.tweener.firebase.auth.provider

/**
 * @author Vivien Mahe
 * @since 26/07/2024
 */
class MissingSignInParamsException(provider: FirebaseAuthProvider<*>) : IllegalArgumentException("SignIn parameters are mandatory for " + provider::class.simpleName)

