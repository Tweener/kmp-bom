import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("multiplatform").version(Dependencies.Versions.kotlin).apply(false)
    id("com.android.library").version(Dependencies.Versions.gradle).apply(false)
    kotlin("plugin.serialization").version(Dependencies.Versions.kotlin).apply(false)
    kotlin("plugin.parcelize").version(Dependencies.Versions.kotlin).apply(false)
    id("org.jetbrains.dokka").version(Dependencies.Versions.dokka).apply(false)
    id("io.github.gradle-nexus.publish-plugin").version(Dependencies.Versions.nexusSonatype)
    id("io.github.gradlebom.generator-plugin").version(Dependencies.Versions.bomGeneratorPlugin).apply(false)
    id("com.github.ben-manes.versions").version(Dependencies.Versions.dependencyVersionsPlugin) // ./gradlew dependencyUpdates
//    id("io.realm.kotlin").version(Dependencies.Versions.realm).apply(false)
}

buildscript {
    dependencies {
        classpath("io.realm.kotlin:gradle-plugin:${Dependencies.Versions.realm}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(gradleLocalProperties(rootDir).getProperty("sonatype.username") ?: System.getenv("OSSRH_USERNAME"))
            password.set(gradleLocalProperties(rootDir).getProperty("sonatype.password") ?: System.getenv("OSSRH_PASSWORD"))
            stagingProfileId.set(gradleLocalProperties(rootDir).getProperty("sonatype.stagingProfileId") ?: System.getenv("OSSRH_STAGING_PROFILE_ID"))
        }
    }
}
