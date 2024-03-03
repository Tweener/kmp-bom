plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

android {
    namespace = BomConfiguration.Libraries.Common.namespace
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
        sourceCompatibility = Dependencies.Versions.Compiler.javaCompatibility
        targetCompatibility = Dependencies.Versions.Compiler.javaCompatibility

        isCoreLibraryDesugaringEnabled = true
    }

    dependencies {
        coreLibraryDesugaring(Dependencies.Libraries.Android.desugarJdkLibs)
    }
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        publishLibraryVariants("release")

        compilations.all {
            kotlinOptions {
                jvmTarget = Dependencies.Versions.Compiler.jvmTarget
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "kmp-common"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {
            implementation(Dependencies.Libraries.napier)
            implementation(Dependencies.Libraries.annotations)

            // Coroutines
            implementation(Dependencies.Libraries.Coroutines.core)
        }

        androidMain.dependencies {
            // Coroutines
            implementation(Dependencies.Libraries.Coroutines.Android.android)

            // Android
            implementation(Dependencies.Libraries.Android.AndroidX.core)
        }

        iosMain.dependencies {

        }
    }
}

// region Publishing

// Dokka configuration
val dokkaOutputDir = buildDir.resolve("dokka")
tasks.dokkaHtml { outputDirectory.set(file(dokkaOutputDir)) }
val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") { delete(dokkaOutputDir) }
val javadocJar = tasks.create<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    from(dokkaOutputDir)
}

group = MavenPublishing.group
version = BomConfiguration.Libraries.Common.version

publishing {
    publications {
        publications.withType<MavenPublication> {
            artifact(javadocJar)

            pom {
                name.set(MavenPublishing.Libraries.Common.name)
                description.set(MavenPublishing.Libraries.Common.description)
                url.set(MavenPublishing.Libraries.Common.packageUrl)

                licenses {
                    license {
                        name.set(MavenPublishing.License.name)
                        url.set(MavenPublishing.License.url)
                    }
                }

                issueManagement {
                    system.set(MavenPublishing.IssueManagement.system)
                    url.set("${MavenPublishing.Libraries.Common.packageUrl}/issues")
                }

                developers {
                    developer {
                        id.set(MavenPublishing.Developer.id)
                        name.set(MavenPublishing.Developer.name)
                        email.set(MavenPublishing.Developer.email)
                    }
                }

                scm {
                    connection.set("scm:git:git://${MavenPublishing.Libraries.Common.gitUrl}")
                    developerConnection.set("scm:git:ssh://${MavenPublishing.Libraries.Common.gitUrl}")
                    url.set(MavenPublishing.Libraries.Common.packageUrl)
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

// endregion Publishing
