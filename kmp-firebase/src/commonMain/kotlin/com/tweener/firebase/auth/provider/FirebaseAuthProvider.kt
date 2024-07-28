package com.tweener.firebase.auth.provider

import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * FirebaseAuthProvider class for handling Firebase Authentication.
 *
 * This abstract class defines the common interface and shared functionality for platform-specific authentication implementations.
 *
 * @param SignInParams The type of the parameters required for the sign-in process.
 * @param firebaseAuthDataSource The data source for Firebase authentication.
 *
 * @author Vivien Mahe
 * @since 26/07/2024
 */
abstract class FirebaseAuthProvider<SignInParams>(
    protected val firebaseAuthDataSource: FirebaseAuthDataSource,
) {

    /**
     * Abstract method to initiate the Google sign-in process.
     *
     * @param params The parameters required for the sign-in process.
     * @param onResponse Callback to handle the result of the sign-in process. It returns a Result object containing a FirebaseUser on success, or an exception on failure.
     */
    abstract suspend fun signIn(params: SignInParams? = null, onResponse: (Result<FirebaseUser>) -> Unit)

    /**
     * Signs out the current user.
     */
    suspend fun signOut() {
        firebaseAuthDataSource.signOut()
    }

    /**
     * Checks if a user is currently signed in.
     *
     * @return A Flow emitting a Boolean indicating whether a user is logged in.
     */
    fun isUserSignedIn(): Flow<Boolean> = firebaseAuthDataSource.isUserLoggedIn()

    /**
     * Retrieves the currently signed-in user.
     *
     * @return The currently signed-in FirebaseUser, or null if no user is signed in.
     */
    fun getCurrentUser(): Flow<FirebaseUser?> = firebaseAuthDataSource.getCurrentUser()

    /**
     * Asserts the given [params] are not null or throws a [MissingSignInParamsException].
     *
     * @param params The parameters required for the sign-in process.
     * @throws MissingSignInParamsException
     */
    @OptIn(ExperimentalContracts::class)
    protected fun assertSignInParamsNotNull(params: SignInParams? = null): SignInParams {
        contract {
            returns() implies (params != null)
        }

        if (params == null) {
            throw MissingSignInParamsException(this)
        }

        return params
    }
}
