val gdxVersion: String by project
val kotlinVersion: String by project
val ktxVersion: String by project
val ashleyVersion: String by project

plugins {
    kotlin("jvm")
    `java-library`
    idea
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    api("com.badlogicgames.ashley:ashley:$ashleyVersion")
    api("com.badlogicgames.gdx:gdx:$gdxVersion")
    api("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
    api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    api("io.github.libktx:ktx-app:$ktxVersion")
    api("io.github.libktx:ktx-ashley:$ktxVersion")
    api("io.github.libktx:ktx-assets:$ktxVersion")
    api("io.github.libktx:ktx-box2d:$ktxVersion")
    api("io.github.libktx:ktx-collections:$ktxVersion")
    api("io.github.libktx:ktx-graphics:$ktxVersion")
    api("io.github.libktx:ktx-inject:$ktxVersion")
    api("io.github.libktx:ktx-log:$ktxVersion")
    api("io.github.libktx:ktx-tiled:$ktxVersion")
}
