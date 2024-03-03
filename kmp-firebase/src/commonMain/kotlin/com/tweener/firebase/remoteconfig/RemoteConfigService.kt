package com.tweener.firebase.remoteconfig

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import dev.gitlive.firebase.remoteconfig.get
import dev.gitlive.firebase.remoteconfig.remoteConfig
import io.github.aakira.napier.Napier

/**
 * @author Vivien Mahe
 * @since 16/12/2023
 */
class RemoteConfigService(
    private val isDebug: Boolean,
) {

    suspend fun getConfig(): FirebaseRemoteConfig =
        Firebase.remoteConfig
            .apply {
                if (isDebug) {
                    // Force refresh to 10 seconds when in Debug
                    settings {
                        minimumFetchIntervalInSeconds = 10
                    }
                }
            }
            .also { it.fetchAndActivate() }

    suspend inline fun <reified T> get(key: String, defaultValue: T): T =
        try {
            getConfig().get(key)
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't get RemoteConfig key $key! Returning default value instead: $defaultValue" }
            defaultValue
        }
}
