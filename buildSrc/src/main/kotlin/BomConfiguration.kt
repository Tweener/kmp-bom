/**
 * @author Vivien Mahe
 * @since 02/03/2024
 */

object BomConfiguration {

    // BoM configuration
    const val artifactId = "kmp-bom"
    const val version = "2.4.0"
    const val compileSDK = 34
    const val minSDK = 24

    object Libraries {

        object Firebase {
            const val packageName = "com.tweener.firebase"
            const val version = "1.3.0"
            const val namespace = "$packageName.android"
        }
    }
}
