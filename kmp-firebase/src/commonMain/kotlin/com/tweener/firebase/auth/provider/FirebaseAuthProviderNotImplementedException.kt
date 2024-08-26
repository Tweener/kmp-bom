package com.tweener.firebase.auth.provider

import kotlin.reflect.KClass

/**
 * @author Vivien Mahe
 * @since 25/08/2024
 */
class FirebaseAuthProviderNotImplementedException(klass: KClass<out FirebaseAuthProvider<*>>) :
    UnsupportedOperationException("Firebase Authentication is not yet implemented for provider ${klass.simpleName}")
