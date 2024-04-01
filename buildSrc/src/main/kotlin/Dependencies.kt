import org.gradle.api.JavaVersion

/**
 * @author Vivien Mahe
 * @since 23/07/2022
 */

object Dependencies {

    object Versions {

        const val kotlin = "1.9.21"
        const val gradle = "8.1.4"
        const val dependencyVersionsPlugin = "0.51.0"
        const val nexusSonatype = "2.0.0-rc-1"
        const val dokka = "1.9.10"
        const val bomGeneratorPlugin = "1.0.0.Final"
        const val annotations = "1.7.1"
        const val coroutines = "1.7.3"
        const val napier = "2.6.1"
        const val kotlinxDatetime = "0.6.0-RC.2"
        const val realm = "1.13.0"
        const val firebaseGitLiveApp = "1.11.1" // Firebase for KMP https://firebaseopensource.com/projects/gitliveapp/firebase-kotlin-sdk/

        object Compiler {
            const val jvmTarget = "17"
            val javaCompatibility = JavaVersion.VERSION_17
        }

        object Tweener {
            const val charts = "1.0.1"
        }

        object KotlinX {
            const val serializationJson = "1.6.2"
        }

        object Android {
            const val desugarJdkLibs = "2.0.3"

            object AndroidX {
                const val core = "1.12.0"
            }
        }
    }

    object Libraries {

        const val napier = "io.github.aakira:napier:${Versions.napier}"
        const val kotlinXDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDatetime}"
        const val annotations = "androidx.annotation:annotation:${Versions.annotations}"
        const val realm = "io.realm.kotlin:library-base:${Versions.realm}"

        object Tweener {
            const val charts = "io.github.tweener:kmp-charts:${Versions.Tweener.charts}"
        }

        object KotlinX {
            const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KotlinX.serializationJson}"
        }

        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

            object Android {
                const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
            }
        }

        object FirebaseGitLiveApp {
            const val firestore = "dev.gitlive:firebase-firestore:${Versions.firebaseGitLiveApp}"
            const val config = "dev.gitlive:firebase-config:${Versions.firebaseGitLiveApp}"
            const val auth = "dev.gitlive:firebase-auth:${Versions.firebaseGitLiveApp}"
            const val functions = "dev.gitlive:firebase-functions:${Versions.firebaseGitLiveApp}"
            const val crashlytics = "dev.gitlive:firebase-crashlytics:${Versions.firebaseGitLiveApp}"
        }

        object Android {
            const val desugarJdkLibs = "com.android.tools:desugar_jdk_libs:${Versions.Android.desugarJdkLibs}"

            object AndroidX {
                const val core = "androidx.core:core-ktx:${Versions.Android.AndroidX.core}"
            }
        }
    }
}
