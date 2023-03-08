pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        val kotlinVersion: String by extra
        val agpVersion: String by extra
        val composeVersion: String by extra
        kotlin("multiplatform") version kotlinVersion
        kotlin("android") version kotlinVersion
        id("com.android.application") version agpVersion
        id("com.android.library") version agpVersion
        id("org.jetbrains.compose") version composeVersion
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
    }
}

rootProject.name = "kiro-app"

include(":android", ":desktop", ":common")

include(":kiro-api:api")
project(":kiro-api:api").projectDir = file("../kiro-api/api")