package com.tweener.firebase.auth.provider.google

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

data class FirebaseGoogleAuthParams(
    val retry: Int = 1,
    val delay: Duration = 1.seconds
) {
    constructor(retry: Int, delayMillis: Long) : this(retry, delayMillis.milliseconds)
}
