[![Website](https://img.shields.io/badge/Author-vivienmahe.com-orange)](https://vivienmahe.com/)
[![X/Twitter](https://img.shields.io/twitter/follow/VivienMahe)](https://twitter.com/VivienMahe)

[![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fs01.oss.sonatype.org%2Fservice%2Flocal%2Frepo_groups%2Fpublic%2Fcontent%2Fio%2Fgithub%2Ftweener%2Fkmp-bom%2Fmaven-metadata.xml)](https://central.sonatype.com/artifact/io.github.tweener/kmp-bom)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
![gradle-version](https://img.shields.io/badge/gradle-8.5.2-blue?logo=gradle)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

---

# kmp-bom

The [Bill of Materials](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms) (BoM) for this project serves as a centralized reference specifying the versions of several Kotlin Multiplatform dependencies used, ensuring consistency and streamlined dependency management across all modules that implement it.

#### Included libraries:
- **[kmp-firebase](https://github.com/Tweener/kmp-bom/tree/main/kmp-firebase)**: A wrapper for [GitLiveApp Firebase SDK](https://github.com/GitLiveApp/firebase-kotlin-sdk) which provides a more straightforward configuration and usage.

## 💾 Installation

Add the BoM dependency to the `sourceSet` of your module and include any specific dependencies you wish to use:

```groovy
implementation(project.dependencies.platform("io.github.tweener:kmp-bom:$kmp-bom_version")) // Mandatory
implementation("io.github.tweener:kmp-firebase")
```

_The latest version is: [![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fs01.oss.sonatype.org%2Fservice%2Flocal%2Frepo_groups%2Fpublic%2Fcontent%2Fio%2Fgithub%2Ftweener%2Fkmp-bom%2Fmaven-metadata.xml)](https://central.sonatype.com/artifact/io.github.tweener/kmp-bom)_
