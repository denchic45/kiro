package com.denchic45.backend

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.denchic45.backend.plugins.configureDatabases
import com.denchic45.backend.plugins.configureRouting
import com.denchic45.backend.plugins.configureSecurity
import com.denchic45.backend.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureRouting()
}
