package com.denchic45.kiro.api.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val firstName: String,
    val surname: String,
    val patronymic: String? = null,
    val email: String,
    val password: String
)
