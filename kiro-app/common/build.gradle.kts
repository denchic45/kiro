val ktorVersion: String by project
val decomposeVersion: String by project

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

group = "com.denchic45.kiro"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(11)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.material3)

                api(project(":kiro-api:api"))

                // Ktor client
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-client-auth:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")

                // Decompose
                api("com.arkivanov.decompose:decompose:$decomposeVersion")

                // kotlin-result
                implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.16")

                api("com.russhwolf:multiplatform-settings:1.0.0")
                api("com.russhwolf:multiplatform-settings-coroutines:1.0.0")

                api("me.tatarka.inject:kotlin-inject-runtime:0.6.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.9.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
                // Decompose
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
            }
        }
        val desktopTest by getting
    }
}

//kotlin.sourceSets.commonMain {
//    kotlin.srcDir("build/generated/ksp/")
//}

dependencies {
    add("kspAndroid", "me.tatarka.inject:kotlin-inject-compiler-ksp:0.6.1")
    add("kspDesktop", "me.tatarka.inject:kotlin-inject-compiler-ksp:0.6.1")
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}