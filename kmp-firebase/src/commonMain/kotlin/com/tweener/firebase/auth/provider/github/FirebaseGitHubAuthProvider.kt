package com.tweener.firebase.auth.provider.github

import com.tweener.firebase.auth.datasource.FirebaseAuthDataSource
import com.tweener.firebase.auth.provider.FirebaseAuthProvider
import dev.gitlive.firebase.auth.OAuthProvider

abstract class FirebaseGitHubAuthProvider(
    firebaseAuthDataSource: FirebaseAuthDataSource,
    protected val oAuthProvider: OAuthProvider
): FirebaseAuthProvider<FirebaseGitHubAuthParams>(firebaseAuthDataSource) {

    constructor(
        firebaseAuthDataSource: FirebaseAuthDataSource,
        scopes: Collection<String>
    ) : this(
        firebaseAuthDataSource = firebaseAuthDataSource,
        oAuthProvider = OAuthProvider(
            provider = "github.com",
            scopes = scopes.toList(),
            auth = firebaseAuthDataSource.firebaseAuthService.auth
        )
    )

    constructor(
        firebaseAuthDataSource: FirebaseAuthDataSource,
        vararg scope: String
    ) : this(
        firebaseAuthDataSource = firebaseAuthDataSource,
        scopes = scope.toList()
    )
}