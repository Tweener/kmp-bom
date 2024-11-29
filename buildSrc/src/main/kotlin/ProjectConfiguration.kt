import org.gradle.api.JavaVersion

/**
 * @author Vivien Mahe
 * @since 23/07/2022
 */

object ProjectConfiguration {

    object Compiler {
        val javaCompatibility = JavaVersion.VERSION_21
        val jvmTarget = javaCompatibility.toString()
    }

    object iOS {
        const val deploymentTarget = "12.0"
    }
}
