/**
 * @author Vivien Mahe
 * @since 02/03/2024
 */

object MavenPublishing {

    const val group = "io.github.tweener"

    object Repository {
        object Maven {
            const val name = "OSSRH"
            const val url = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2"
        }
    }

    object Developer {
        const val id = "Tweener"
        const val name = "Vivien Mahé"
        const val email = "vivien@tweener-labs.com"
    }

    object License {
        const val name = "The Apache License, Version 2.0"
        const val url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
    }

    object IssueManagement {
        const val system = "GitHub Issues"
    }

    object Libraries {

        object Bom {
            const val name = "KMPBom"
            const val description = "Bill of Materials (BoM) for all Tweener Kotlin Multiplatform libraries"
            const val packageUrl = "https://github.com/Tweener/kmp-bom"
            const val gitUrl = "github.com:Tweener/kmp-bom.git"
        }

        object Common {
            const val name = "KMPCommon"
            const val description = "All Tweener commons stuff for Kotlin Multiplatform"
            const val packageUrl = "https://github.com/Tweener/kmp-common"
            const val gitUrl = "github.com:Tweener/kmp-common.git"
        }

        object Firebase {
            const val name = "KMPFirebase"
            const val description = "A Kotlin Multiplatform library wrappers of GitLiveApp Firebase SDK to provide a more straightforward implementation."
            const val packageUrl = "https://github.com/Tweener/kmp-firebase"
            const val gitUrl = "github.com:Tweener/kmp-firebase.git"
        }

        object Realm {
            const val name = "KMPRealm"
            const val description = "A Kotlin Multiplatform library wrapper to Realm Kotlin"
            const val packageUrl = "https://github.com/Tweener/kmp-realm"
            const val gitUrl = "github.com:Tweener/kmp-realm.git"
        }
    }
}
