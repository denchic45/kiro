package com.kiro

import com.sksamuel.hoplite.ConfigAlias
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import java.util.*


data class JwtConf(val audience: String, val secret: String,val realm: String)

data class DatabaseConf(val url: String, val user: String, val password: String)

data class SmtpConf(
    val host: String,
    val port: Int,
    @ConfigAlias("use-ssl") val ssl: Boolean,
    val username: String,
    val password: String
)

data class Config(
    val ktor:KtorConf,
    val jwt: JwtConf,
    val database: DatabaseConf,
    val smtp: SmtpConf
)

data class KtorConf(
    val deployment: DeploymentConf
)

data class DeploymentConf(
    val port:Int,
)

val config: Config
    get() = ConfigLoaderBuilder.default()
        .addResourceSource("/application.conf")
        .build()
        .loadConfigOrThrow()