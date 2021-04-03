val gdxVersion: String by project
val kotlinVersion: String by project
val ktxVersion: String by project

plugins {
    kotlin("jvm")
    `java-library`
    idea
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    api("com.badlogicgames.gdx:gdx:$gdxVersion")
    api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    api("io.github.libktx:ktx-app:$ktxVersion")
    api("io.github.libktx:ktx-collections:$ktxVersion")
    api("io.github.libktx:ktx-graphics:$ktxVersion")
    api("io.github.libktx:ktx-inject:$ktxVersion")
    api("io.github.libktx:ktx-log:$ktxVersion")
}
