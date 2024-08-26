package com.tweener.firebase.auth.provider.email

/**
 * @author Vivien Mahe
 * @since 26/07/2024
 */
data class ForgotPasswordParams(
    val email: String,
    val url: String,
    val iosParams: ForgotPasswordIosParams? = null,
    val androidParams: ForgotPasswordAndroidParams? = null,
    val canHandleCodeInApp: Boolean,
)

data class ForgotPasswordIosParams(
    val iOSBundleId: String,
)

data class ForgotPasswordAndroidParams(
    val androidPackageName: String,
    val installIfNotAvailable: Boolean = true,
    val minimumVersion: String? = null,
)
