/**
 * @author Vivien Mahe
 * @since 02/03/2024
 */

object BomConfiguration {

    // BoM configuration
    const val artifactId = "kmp-bom"
    const val version = "1.0.0"
    const val compileSDK = 34
    const val minSDK = 24

    object Libraries {

        object Realm {
            const val packageName = "com.tweener.realm"
            const val version = "1.0.1"
            const val namespace = "$packageName.android"
        }
    }
}
