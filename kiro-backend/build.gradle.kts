import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val koinVersion: String by project
val logbackVersion: String by project
val exposedVersion : String by project
val h2Version : String by project
val postgresVersion : String by project

plugins {
    application
    kotlin("jvm") version "1.7.20"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"

    // Shadow plugin - enable support for building our UberJar
    id("com.github.johnrengelman.shadow") version "7.1.2"
    war
}

group = "com.kiro-backend"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    google()
    flatDir {
        dirs("libs/")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks {
    val shadowJarTask = named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        // explicitly configure the filename of the resulting UberJar
        val uberJarFileName = "com.denchic45.kiro-backend-0.0.1.jar"
        archiveFileName.set(uberJarFileName)

        // Appends entries in META-INF/services resources into a single resource. For example, if there are several
        // META-INF/services/org.apache.maven.project.ProjectBuilder resources spread across many JARs the individual
        // entries will all be concatenated into a single META-INF/services/org.apache.maven.project.ProjectBuilder
        // resource packaged into the resultant JAR produced by the shading process -
        // Effectively ensures we bring along all the necessary bits from Jetty
        mergeServiceFiles()

        // As per the App Engine java11 standard environment requirements listed here:
        // https://cloud.google.com/appengine/docs/standard/java11/runtime
        // Your Jar must contain a Main-Class entry in its META-INF/MANIFEST.MF metadata file
        manifest {
            attributes(mapOf("Main-Class" to application.mainClass.get()))
        }
    }

    // because we're using shadowJar, this task has limited value
    named("jar") {
        enabled = false
    }

    // update the `assemble` task to ensure the creation of a brand new UberJar using the shadowJar task
    named("assemble") {
        dependsOn(shadowJarTask)
    }
}

dependencies {
//    implementation(project(":kiro-api:api"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("com.h2database:h2:$h2Version")
    implementation("org.postgresql:postgresql:$postgresVersion")

    // Ktor server
    implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-request-validation:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")

    // Ktor client
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-auth:$ktorVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    implementation("com.sksamuel.hoplite:hoplite-hocon:2.7.2")

    implementation("org.mindrot:jbcrypt:0.4")

    implementation("org.apache.commons:commons-email:1.5")
}