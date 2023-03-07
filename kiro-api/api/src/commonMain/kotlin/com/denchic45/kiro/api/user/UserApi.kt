package com.denchic45.kiro.api.user

import com.denchic45.kiro.common.EmptyResponseResult
import com.denchic45.kiro.common.ResponseResult
import com.denchic45.kiro.common.toResult
import com.denchic45.kiro.api.user.model.CreateUserRequest
import com.denchic45.kiro.api.user.model.UserResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.util.*

class UserApi(private val client: HttpClient) {
    suspend fun create(createUserRequest: CreateUserRequest): ResponseResult<UserResponse> {
        return client.post("/users") {
            contentType(ContentType.Application.Json)
            setBody(createUserRequest)
        }.toResult()
    }

    suspend fun getMe(): ResponseResult<UserResponse> {
        return client.get("/users/me").toResult()
    }

    suspend fun getById(userId: UUID): ResponseResult<UserResponse> {
        return client.get("/users/$userId").toResult()
    }

    suspend fun search(query: String): ResponseResult<List<UserResponse>> {
        return client.get("/users") {
            parameter("q", query)
        }.toResult()
    }

    suspend fun delete(userId: UUID): EmptyResponseResult {
        return client.delete("/users/$userId").toResult()
    }
}