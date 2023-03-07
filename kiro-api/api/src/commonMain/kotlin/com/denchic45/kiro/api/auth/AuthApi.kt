package com.denchic45.kiro.api.auth

import com.denchic45.kiro.api.auth.model.RefreshTokenRequest
import com.denchic45.kiro.api.auth.model.SignInByEmailPasswordRequest
import com.denchic45.kiro.api.auth.model.SignupRequest
import com.denchic45.kiro.api.auth.model.TokenResponse
import com.denchic45.kiro.common.EmptyResponseResult
import com.denchic45.kiro.common.ResponseResult
import com.denchic45.kiro.common.toResult
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthApi(private val client: HttpClient) {
    suspend fun signup(signupRequest: SignupRequest): EmptyResponseResult {
        return client.post("/auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(signupRequest)
        }.toResult()
    }

    suspend fun signInByEmailPassword(signInByEmailPasswordRequest: SignInByEmailPasswordRequest): ResponseResult<TokenResponse> {
        return client.post("/auth/token") {
            contentType(ContentType.Application.Json)
            setBody(signInByEmailPasswordRequest)
            parameter("grant_type", "password")
        }.toResult()
    }

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): ResponseResult<TokenResponse> {
        return client.post("/auth/token") {
            contentType(ContentType.Application.Json)
            parameter("grant_type", "refresh_token")
            setBody(refreshTokenRequest)
        }.toResult()
    }
}