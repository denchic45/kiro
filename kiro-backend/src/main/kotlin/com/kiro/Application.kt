package com.kiro

import com.kiro.di.configureDI
import com.kiro.feature.auth.configureAuth
import com.kiro.feature.course.configureCourses
import com.kiro.feature.studygroup.configureStudyGroups
import com.kiro.feature.user.configureUsers
import com.kiro.plugins.configureSerialization
import com.kiro.plugins.configureStatusPages
import com.studiversity.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject


fun main() {
    embeddedServer(Netty, port = config.ktor.deployment.port, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

//fun Application.module() {
//    configureSerialization()
//    configureDatabases()
//    configureSecurity()
//    configureRouting()
//}

@Suppress("unused")
fun Application.module() = runBlocking {
    configureDI()
    configureSerialization()
    configureStatusPages()
//    configureDatabase()
    configureAuth()
    configureUsers()
    configureCourses()
    configureStudyGroups()
}

private fun Application.configureDatabase() {
    val databaseFactory: DatabaseFactory by inject()
    databaseFactory.database
}