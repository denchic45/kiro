package com.kiro.ktor

import com.auth0.jwt.interfaces.Payload
import com.kiro.util.toUUID
import java.util.*

val Payload.claimId: UUID
    get() = getClaim("sub").asString().toUUID()
