import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.bom.generator)
    id("signing")
}

// region Publishing

group = MavenPublishing.group
version = BomConfiguration.version

bomGenerator {
    includeDependency(libs.tweener.charts.get().toString())
}

publishing {
    repositories {
        maven {
            name = MavenPublishing.Repository.Maven.name
            url = uri(MavenPublishing.Repository.Maven.url)
            credentials {
                username = gradleLocalProperties(rootDir).getProperty("sonatype.token.username") ?: System.getenv("OSSRH_USER_TOKEN_USERNAME")
                password = gradleLocalProperties(rootDir).getProperty("sonatype.token.username") ?: System.getenv("OSSRH_USER_TOKEN_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("Bom") {
            artifactId = BomConfiguration.artifactId

            pom {
                name.set(MavenPublishing.Libraries.Bom.name)
                description.set(MavenPublishing.Libraries.Bom.description)
                url.set(MavenPublishing.Libraries.Bom.packageUrl)

                licenses {
                    license {
                        name.set(MavenPublishing.License.name)
                        url.set(MavenPublishing.License.url)
                    }
                }

                issueManagement {
                    system.set(MavenPublishing.IssueManagement.system)
                    url.set("${MavenPublishing.Libraries.Bom.packageUrl}/issues")
                }

                developers {
                    developer {
                        id.set(MavenPublishing.Developer.id)
                        name.set(MavenPublishing.Developer.name)
                        email.set(MavenPublishing.Developer.email)
                    }
                }

                scm {
                    connection.set("scm:git:git://${MavenPublishing.Libraries.Bom.gitUrl}")
                    developerConnection.set("scm:git:ssh://${MavenPublishing.Libraries.Bom.gitUrl}")
                    url.set(MavenPublishing.Libraries.Bom.packageUrl)
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
