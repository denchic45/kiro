package com.kiro.feature.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kiro.config
import com.kiro.feature.auth.usecase.RefreshTokenUseCase
import com.kiro.api.auth.AuthErrors
import com.kiro.api.auth.model.SignupRequest
import com.kiro.api.auth.model.TokenResponse
import com.kiro.feature.auth.usecase.SignInByEmailAndPasswordUseCase
import com.kiro.feature.auth.usecase.SignUpUseCase
import com.kiro.util.toDate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.time.LocalDateTime

fun Route.signupRoute() {
    val signup: SignUpUseCase by inject()

    post("/signup") {
        val signupRequest = call.receive<SignupRequest>()
        signup(signupRequest)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.tokenRoute() {
    val signInByEmailAndPasswordUseCase: SignInByEmailAndPasswordUseCase by inject()
    val refreshToken: RefreshTokenUseCase by inject()

    post("/token") {
        val userIdWithToken = when (call.request.queryParameters["grant_type"]) {
            "password" -> signInByEmailAndPasswordUseCase(call.receive())
            "refresh_token" -> refreshToken(call.receive())
            else -> throw BadRequestException(AuthErrors.INVALID_GRANT)
        }

        val token = JWT.create()
            .withAudience(config.jwt.audience)
            .withSubject(userIdWithToken.first.toString())
            .withExpiresAt(LocalDateTime.now().plusDays(1).toDate())
            .sign(Algorithm.HMAC256(config.jwt.secret))

        call.respond(HttpStatusCode.OK, TokenResponse(token, userIdWithToken.second))
    }
}