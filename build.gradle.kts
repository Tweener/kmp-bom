import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.kotlin.parcelize).apply(false)
    alias(libs.plugins.kotlin.nativeCocoaPods).apply(false)
    alias(libs.plugins.dokka)
    alias(libs.plugins.nexus.sonatype)
    alias(libs.plugins.dependency.versions) // ./gradlew dependencyUpdates
    alias(libs.plugins.bom.generator).apply(false)
    alias(libs.plugins.realm).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(gradleLocalProperties(rootDir, providers).getProperty("sonatype.username") ?: System.getenv("OSSRH_USERNAME"))
            password.set(gradleLocalProperties(rootDir, providers).getProperty("sonatype.password") ?: System.getenv("OSSRH_PASSWORD"))
            stagingProfileId.set(gradleLocalProperties(rootDir, providers).getProperty("sonatype.stagingProfileId") ?: System.getenv("OSSRH_STAGING_PROFILE_ID"))
        }
    }
}

subprojects {
    // Dokka configuration
    tasks.register<Jar>("dokkaJavadocJar") {
        dependsOn(tasks.dokkaHtml)
        from(tasks.dokkaHtml.flatMap { it.outputDirectory })
        archiveClassifier.set("javadoc")
    }

    tasks.withType<DokkaTask>().configureEach {
        dokkaSourceSets.configureEach {
            jdkVersion.set(ProjectConfiguration.Compiler.jvmTarget.toInt())
            languageVersion.set(libs.versions.kotlin)

            sourceLink {
                localDirectory.set(rootProject.projectDir)
                remoteUrl.set(URL("https://github.com/Tweener/kmp-bom/tree/main"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}
