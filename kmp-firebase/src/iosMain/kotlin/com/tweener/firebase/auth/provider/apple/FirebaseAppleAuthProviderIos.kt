package com.tweener.firebase.auth.provider.apple

import cocoapods.FirebaseAuth.FIRAuth
import cocoapods.FirebaseAuth.FIROAuthProvider
import com.tweener.common._internal.contract.requireNotNullOrThrow
import com.tweener.common._internal.thread.resumeActive
import com.tweener.common._internal.thread.resumeActiveWithException
import com.tweener.firebase.auth.FirebaseAuthService
import com.tweener.firebase.auth.FirebaseUser
import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProviderUnknownUserException
import com.tweener.firebase.auth.provider.FirebaseProvider
import io.github.aakira.napier.Napier
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AuthenticationServices.ASAuthorization
import platform.AuthenticationServices.ASAuthorizationAppleIDCredential
import platform.AuthenticationServices.ASAuthorizationAppleIDProvider
import platform.AuthenticationServices.ASAuthorizationController
import platform.AuthenticationServices.ASAuthorizationControllerDelegateProtocol
import platform.AuthenticationServices.ASAuthorizationControllerPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASAuthorizationScopeEmail
import platform.AuthenticationServices.ASAuthorizationScopeFullName
import platform.AuthenticationServices.ASPresentationAnchor
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.UIKit.UIApplication
import platform.darwin.NSObject
import kotlin.coroutines.cancellation.CancellationException

/**
 * @author Vivien Mahe
 * @since 23/08/2024
 */
class FirebaseAppleAuthProviderIos(
    firebaseAuthDataSource: FirebaseAuthDataSource = FirebaseAuthDataSource(firebaseAuthService = FirebaseAuthService()),
) : FirebaseAppleAuthProvider(firebaseAuthDataSource = firebaseAuthDataSource) {

    private lateinit var delegate: AuthorizationControllerDelegate
    private val presentationContextProvider = PresentationContextProvider()

    override suspend fun signIn(params: Nothing?): Result<FirebaseUser> = suspendCancellableCoroutine { continuation ->
        try {
            val rawNonce = NonceFactory.createRandomNonceString()
            delegate = AuthorizationControllerDelegate(firebaseAuthDataSource = firebaseAuthDataSource, nonce = rawNonce) { result -> continuation.resumeActive(result) }

            continuation.invokeOnCancellation {
                Napier.d { "Canceled Apple Sign In on iOS" }

                delegate.onResponse = {}

                continuation.resumeActiveWithException(CancellationException())
            }

            val request = ASAuthorizationAppleIDProvider().createRequest().apply {
                requestedScopes = listOf(ASAuthorizationScopeEmail, ASAuthorizationScopeFullName)
                nonce = NonceFactory.sha256(rawNonce)
            }

            ASAuthorizationController(authorizationRequests = listOf(request)).apply {
                delegate = this@FirebaseAppleAuthProviderIos.delegate
                presentationContextProvider = this@FirebaseAppleAuthProviderIos.presentationContextProvider
                performRequests()
            }
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "An error occurred while signing in with Apple provider" }
            continuation.resumeActiveWithException(throwable)
        }
    }
}

private class AuthorizationControllerDelegate(
    private val firebaseAuthDataSource: FirebaseAuthDataSource,
    private val nonce: String,
    var onResponse: (Result<FirebaseUser>) -> Unit,
) : ASAuthorizationControllerDelegateProtocol, NSObject() {

    @OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
    override fun authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization: ASAuthorization) {
        try {
            val appleIDCredential = didCompleteWithAuthorization.credential as ASAuthorizationAppleIDCredential

            val appleIDToken = appleIDCredential.identityToken
            requireNotNullOrThrow(appleIDToken) { FirebaseAppleAuthProviderException(message = "Unable to fetch identity token") }

            val idTokenString = NSString.create(data = appleIDToken, encoding = NSUTF8StringEncoding)?.toString()
            requireNotNullOrThrow(idTokenString) { FirebaseAppleAuthProviderException(message = "Unable to serialize token string from data: ${appleIDToken.debugDescription}") }

            val credential = FIROAuthProvider.appleCredentialWithIDToken(idToken = idTokenString, rawNonce = nonce, fullName = appleIDCredential.fullName)

            FIRAuth.auth().signInWithCredential(credential) { authResult, error ->
                error?.let { Napier.e { "Couldn't sign in with Apple on iOS! $error" } }

                when {
                    error != null || authResult == null -> onResponse(Result.failure(FirebaseAppleAuthProviderException(message = "FIRAuthDataResult is null")))

                    else -> {
                        firebaseAuthDataSource.getCurrentUser()
                            ?.let { user -> onResponse(Result.success(user)) }
                            ?: onResponse(Result.failure(FirebaseAuthProviderUnknownUserException(provider = FirebaseProvider.APPLE)))
                    }
                }
            }
        } catch (throwable: Throwable) {
            onResponse(Result.failure(throwable))
        }
    }

    override fun authorizationController(controller: ASAuthorizationController, didCompleteWithError: NSError) {
        Napier.e { "Didn't get authorization to sign in with Apple: $didCompleteWithError" }
        onResponse(Result.failure(FirebaseAppleAuthProviderException(message = didCompleteWithError.localizedFailureReason)))
    }
}

private class PresentationContextProvider : ASAuthorizationControllerPresentationContextProvidingProtocol, NSObject() {

    override fun presentationAnchorForAuthorizationController(controller: ASAuthorizationController): ASPresentationAnchor =
        UIApplication.sharedApplication.keyWindow?.rootViewController?.view?.window
}
