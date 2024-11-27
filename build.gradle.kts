plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.github.mucahitkayadan"
version = "1.0.2"

repositories {
    mavenCentral()
}

intellij {
    version.set("2023.2.6")
    type.set("IC")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("243.*")
    }
}

tasks.jar {
    archiveBaseName.set("EnvMasker")
    archiveVersion.set("1.0.2")
    manifest {
        attributes["Main-Class"] = "com.github.mucahitkayadan.envmasker.EnvMasker"
    }
}
