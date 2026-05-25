plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.5.0"
}

group = "com.github.mucahitkayadan"
version = "1.0.5"

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
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

intellijPlatform {
    buildSearchableOptions.set(false)
    instrumentCode.set(false)

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
            <h3>1.0.5</h3>
            <ul>
                <li><b>Added:</b>
                    <ul>
                        <li>Setting to control masking of commented-out assignments (e.g. <code># API_KEY=secret</code>)</li>
                        <li>Inline comment handling so values like <code>KEY=value # note</code> mask only the value</li>
                        <li>Removed duplicate <code>.env</code> file type registration to avoid conflicts with JetBrains .env files plugin</li>
                    </ul>
                </li>
            </ul>
        """.trimIndent())
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    
    jar {
        archiveBaseName.set("EnvMasker")
        manifest {
            attributes(
                "Implementation-Title" to "Env File Masker",
                "Implementation-Version" to project.version,
                "Main-Class" to "com.github.mucahitkayadan.envmasker.EnvMasker"
            )
        }
    }
}

tasks.register("cleanBuildPlugin") {
    dependsOn("clean", "buildPlugin")
}
