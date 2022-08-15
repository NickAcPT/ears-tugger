plugins {
    application
}

dependencies {
    // Used to export the skins in the Ears V1 or V2 format
    implementation(project(":ears-main-exporter"))

    // Used to create a command line tool
    implementation("com.github.ajalt.clikt:clikt:3.5.0")
}

application {
    mainClass.set("io.github.nickacpt.earstugger.cli.EntrypointKt")
}