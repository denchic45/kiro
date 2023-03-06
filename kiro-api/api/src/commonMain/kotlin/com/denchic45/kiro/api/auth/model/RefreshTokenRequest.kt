package com.denchic45.kiro.api.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(val refreshToken:String)
