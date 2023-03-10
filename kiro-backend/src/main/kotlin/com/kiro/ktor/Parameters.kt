package com.kiro.ktor

import com.kiro.util.tryToUUID
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.util.*
import java.util.*

fun Parameters.getUuid(name: String): UUID = try {
    getOrFail(name).tryToUUID()
} catch (e: MissingRequestParameterException) {
    throw e
}