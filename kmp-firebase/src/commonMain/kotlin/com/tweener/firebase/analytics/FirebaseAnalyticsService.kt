package com.tweener.firebase.analytics

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.analytics.analytics

/**
 * @author Vivien Mahe
 * @since 23/02/2025
 */
class FirebaseAnalyticsService {

    fun getAnalytics(): FirebaseAnalytics =
        Firebase.analytics
}
