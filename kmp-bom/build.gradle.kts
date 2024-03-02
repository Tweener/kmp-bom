plugins {
    id("io.github.gradlebom.generator-plugin").version(Dependencies.Versions.bomGeneratorPlugin)
}


version = BomConfiguration.version
group = MavenPublishing.group

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = BomConfiguration.artifactId
        }
    }
}
