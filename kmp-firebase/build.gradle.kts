import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
    id("maven-publish")
    id("signing")
}

android {
    namespace = BomConfiguration.Libraries.Firebase.namespace
    compileSdk = BomConfiguration.compileSDK

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = BomConfiguration.minSDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
        }
    }

    compileOptions {
        sourceCompatibility = ProjectConfiguration.Compiler.javaCompatibility
        targetCompatibility = ProjectConfiguration.Compiler.javaCompatibility

        isCoreLibraryDesugaringEnabled = true
    }

    dependencies {
        coreLibraryDesugaring(libs.android.desugarjdklibs)
    }
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        publishLibraryVariants("release")

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(ProjectConfiguration.Compiler.jvmTarget))
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "kmp-firebase"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {
            implementation(libs.napier)
            implementation(libs.kmpkit)

            // Coroutines
            api(libs.kotlin.coroutines.core)

            // Firebase
            api(libs.firebase.firestore)
            implementation(libs.firebase.config)
            api(libs.firebase.auth)
            implementation(libs.firebase.functions)
            api(libs.firebase.crashlytics)
            api(libs.firebase.analytics)

            implementation(libs.kotlin.serialization.json)
        }

        androidMain.dependencies {
            // Coroutines
            api(libs.kotlin.coroutines.android)

            // Android
            implementation(libs.android.core)

            // Google Sign In
            implementation(libs.bundles.googleSignIn)
        }

        iosMain.dependencies {

        }
    }
}

// region Publishing & Signing

group = MavenPublishing.group
version = BomConfiguration.Libraries.Firebase.version

publishing {
    publications {
        publications.withType<MavenPublication> {
            artifact(tasks["dokkaJavadocJar"])

            pom {
                name.set(MavenPublishing.Libraries.Firebase.name)
                description.set(MavenPublishing.Libraries.Firebase.description)
                url.set(MavenPublishing.Libraries.Firebase.packageUrl)

                licenses {
                    license {
                        name.set(MavenPublishing.License.name)
                        url.set(MavenPublishing.License.url)
                    }
                }

                issueManagement {
                    system.set(MavenPublishing.IssueManagement.system)
                    url.set("${MavenPublishing.Libraries.Firebase.packageUrl}/issues")
                }

                developers {
                    developer {
                        id.set(MavenPublishing.Developer.id)
                        name.set(MavenPublishing.Developer.name)
                        email.set(MavenPublishing.Developer.email)
                    }
                }

                scm {
                    connection.set("scm:git:git://${MavenPublishing.Libraries.Firebase.gitUrl}")
                    developerConnection.set("scm:git:ssh://${MavenPublishing.Libraries.Firebase.gitUrl}")
                    url.set(MavenPublishing.Libraries.Firebase.packageUrl)
                }
            }
        }
    }
}

signing {
    if (project.hasProperty("signing.gnupg.keyName")) {
        println("Signing lib...")
        useGpgCmd()
        sign(publishing.publications)
    }
}

// endregion Publishing & Signing
