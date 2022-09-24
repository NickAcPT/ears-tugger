plugins {
    id ("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

dependencies {
    // Used to erase areas from skins in the Ears format
    implementation(project(":ears-utils-eraser"))

    // Used to create a command line tool
    implementation("com.github.ajalt.clikt:clikt:3.5.0")
}

application {
    mainClass.set("io.github.nickacpt.earstugger.utils.cli.EntrypointKt")
}

tasks {
    shadowJar {
        exclude("DebugProbesKt.bin")
        exclude("META-INF/**")
        exclude("**/*.kotlin_metadata")
        manifest {
            attributes(mapOf("Main-Class" to application.mainClass.get()))
        }
    }
}