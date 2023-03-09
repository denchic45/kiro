package com.denchic45.kiro.ktor

import com.auth0.jwt.interfaces.Payload
import com.denchic45.kiro.util.toUUID
import java.util.*

val Payload.claimId: UUID
    get() = getClaim("sub").asString().toUUID()
