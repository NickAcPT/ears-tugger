import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20-Beta" apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    this.group = "io.github.nickacpt.earstugger"
    this.version = "1.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://repo.unascribed.com")
        maven("https://jitpack.io")
        flatDir {
            dir(rootProject.file("libs"))
        }
    }

    this.dependencies {
        "implementation"(kotlin("stdlib-jdk8"))
        if (this@subprojects.name !in arrayOf("core", "cli-utils", "ears-utils-eraser")) {
            "implementation"(project(":core"))
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}