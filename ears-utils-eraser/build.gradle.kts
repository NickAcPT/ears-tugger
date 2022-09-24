dependencies {
    implementation(project(":ears-common-utils")) {
        exclude(module = "core")
    }
    api(project(":core")) {
        exclude(module = "kotlin-reflect")
        exclude(group = "com.fasterxml.jackson.module")
        exclude(group = "com.fasterxml.jackson.dataformat")
    }
}