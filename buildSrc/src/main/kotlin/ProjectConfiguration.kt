import org.gradle.api.JavaVersion

/**
 * @author Vivien Mahe
 * @since 23/07/2022
 */

object ProjectConfiguration {

    object Compiler {
        const val jvmTarget = "19"
        val javaCompatibility = JavaVersion.VERSION_19
    }

    object iOS {
        const val deploymentTarget = "12.0"
    }
}
