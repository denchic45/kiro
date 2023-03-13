package com.kiro.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kiro.config
import io.ktor.server.application.*

fun Application.configureSecurity() {
    
    authentication {
            jwt {
                val jwtAudience = config.jwt.audience
                realm = config.jwt.realm
                verifier(
                    JWT
                        .require(Algorithm.HMAC256("secret"))
                        .withAudience(jwtAudience)
//                        .withIssuer(config.jwt.domain)
                        .build()
                )
                validate { credential ->
                    if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
                }
            }
        }
}
