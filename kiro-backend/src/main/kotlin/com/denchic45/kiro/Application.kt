package com.denchic45.kiro

import com.denchic45.kiro.feature.auth.configureAuth
import com.denchic45.kiro.feature.course.configureCourses
import com.denchic45.kiro.feature.studygroup.configureStudyGroups
import com.denchic45.kiro.feature.user.configureUsers
import com.denchic45.kiro.plugins.configureRouting
import com.denchic45.kiro.plugins.configureSerialization
import com.denchic45.kiro.plugins.configureStatusPages
import com.studiversity.database.DatabaseFactory
import com.denchic45.kiro.di.configureDI
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
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
    configureDatabase()
    configureAuth()
    configureUsers()
    configureCourses()
    configureStudyGroups()
    configureRouting()
}

private fun Application.configureDatabase() {
    val databaseFactory: DatabaseFactory by inject()
    databaseFactory.database
}