import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20-Beta" apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    this.group = "io.github.nickacpt.earstugger"
    this.version = "1.0-SNAPSHOT"

    this.
    repositories {
        mavenCentral()
        maven("https://repo.unascribed.com")
        maven("https://jitpack.io")
        flatDir {
            dir(rootProject.file("libs"))
        }
    }

    this.dependencies {
        "implementation"(kotlin("stdlib"))
        if (this@subprojects.name != "core") {
            "implementation"(project(":core"))
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}