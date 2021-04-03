buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
    dependencies {
        val kotlinVersion: String by project
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

plugins {
    idea
}

subprojects {
    version = "0.0.1"
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io")
    }
}
