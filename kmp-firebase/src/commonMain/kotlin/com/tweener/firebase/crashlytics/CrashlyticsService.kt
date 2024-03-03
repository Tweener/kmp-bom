package com.tweener.firebase.crashlytics

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.FirebaseCrashlytics
import dev.gitlive.firebase.crashlytics.crashlytics

/**
 * @author Vivien Mahe
 * @since 14/02/2024
 */
class CrashlyticsService {

    fun getCrashlytics(): FirebaseCrashlytics =
        Firebase.crashlytics
}
