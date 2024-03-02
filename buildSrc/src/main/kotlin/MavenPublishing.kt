/**
 * @author Vivien Mahe
 * @since 02/03/2024
 */

object MavenPublishing {

    const val group = "io.github.tweener"

    object Developer {
        const val id = "Tweener"
        const val name = "Vivien Mahé"
        const val email = "vivien@tweener-labs.com"
    }

    object Libraries {

        object Realm {
            const val name = "KMPRealm"
            const val description = "A Kotlin Multiplatform library wrapper to Realm Kotlin"
            const val packageUrl = "https://github.com/Tweener/kmp-realm"
            const val gitUrl = "github.com:Tweener/kmp-realm.git"
        }
    }
}
