package com.tweener.firebase.auth.provider.email

data class FirebaseEmailLinkParams(
    val email: String,
    val url: String,
    val iosParams: FirebaseEmailLinkIosParams? = null,
    val androidParams: FirebaseEmailLinkAndroidParams? = null,
    val canHandleCodeInApp: Boolean = false
)

data class FirebaseEmailLinkIosParams(
    val iOSBundleId: String,
)

data class FirebaseEmailLinkAndroidParams(
    val androidPackageName: String,
    val installIfNotAvailable: Boolean = true,
    val minimumVersion: String? = null,
)