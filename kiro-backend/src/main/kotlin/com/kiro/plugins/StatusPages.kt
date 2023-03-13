package com.kiro.plugins

import com.kiro.ktor.ForbiddenException
import com.kiro.util.ErrorInfo
import com.kiro.util.respondWithError
import com.kiro.util.respondWithErrors
import com.kiro.util.toErrors
import com.kiro.ktor.ConflictException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> { call, exception ->
            call.respondWithError(
                HttpStatusCode.BadRequest,
                ErrorInfo(
                    if (exception.message == "Illegal input") {
                        "INVALID_INPUT"
                    } else exception.message ?: ""
                )
            )
        }
        exception<NotFoundException> { call, exception ->
            call.respondWithError(HttpStatusCode.NotFound, ErrorInfo(exception.message ?: ""))
        }
        exception<ForbiddenException> { call, exception ->
            call.respondWithError(HttpStatusCode.Forbidden, ErrorInfo(exception.message ?: ""))
        }
        exception<RequestValidationException> { call, exception ->
            call.respondWithErrors(HttpStatusCode.BadRequest, exception.reasons.toErrors())
        }
        exception<ConflictException> { call, exception ->
            call.respondWithError(HttpStatusCode.Conflict, ErrorInfo(exception.message ?: ""))
        }
    }
}