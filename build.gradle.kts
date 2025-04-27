plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.5.0"
}

group = "com.github.mucahitkayadan"
version = "1.0.4"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        // Set the IntelliJ Platform version
        intellijIdeaCommunity("2023.2.6")
    }
}

intellijPlatform {
    // Plugin configuration
    pluginConfiguration {
        id.set("com.github.mucahitkayadan.envmasker")
        name.set("Env File Masker")
        version.set(project.version.toString())
        
        // Set compatibility range
        ideaVersion {
            sinceBuild.set("232")
            untilBuild.set(provider { null })  
        }
        
        // Plugin description and change notes
        description.set("""
            A security-focused plugin that automatically masks sensitive values in .env files.
            
            Features:
            - Automatically detects and masks values in .env files
            - Click to reveal/hide sensitive information
            - Works with all IntelliJ-based IDEs
            - Helps prevent accidental exposure of sensitive data
            
            Perfect for teams working with environment variables containing sensitive information like API keys, passwords, and tokens.
        """.trimIndent())
        
        changeNotes.set("""
            <h3>1.0.4</h3>
            <ul>
                <li><b>Changed:</b>
                    <ul>
                        <li>Extended compatibility to support IntelliJ 2024.1 (build 251) and future versions</li>
                    </ul>
                </li>
            </ul>
        """.trimIndent())
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
}

tasks.register("cleanBuildPlugin") {
    dependsOn("clean", "buildPlugin")
}
