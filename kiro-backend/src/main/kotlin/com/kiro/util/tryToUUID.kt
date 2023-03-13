package com.kiro.util

import io.ktor.server.plugins.*
import java.util.*

fun String.tryToUUID(): UUID = try {
    toUUID()
} catch (t: Throwable) {
    throw BadRequestException("INVALID_UUID")
}