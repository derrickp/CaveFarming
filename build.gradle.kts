buildscript {
    dependencies {
        val kotlinVersion: String by project
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

plugins {
    idea
    id("io.gitlab.arturbosch.detekt").version("1.16.0")
    id("org.jmailen.kotlinter").version("3.4.4")
    kotlin("jvm").version("1.5.0")
    kotlin("plugin.serialization").version("1.5.0")
}

allprojects {
    version = "0.0.1"
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io")
    }
}
