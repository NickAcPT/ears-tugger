plugins {
    application
}

dependencies {
    // Used to export the skins in the Ears V1 format
    implementation(project(":ears-main-exporter"))

    // Used to import the skins in the Ears format
    implementation(project(":ears-main-importer"))

    // Used to create a command line tool
    implementation("com.github.ajalt.clikt:clikt:3.5.0")
}

application {
    mainClass.set("io.github.nickacpt.earstugger.cli.EntrypointKt")
}