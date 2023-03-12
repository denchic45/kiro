package com.denchic45.kiro.api.course

import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.api.course.model.CreateCourseRequest
import com.denchic45.kiro.common.ResponseResult
import com.denchic45.kiro.common.toResult
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.util.*

class CourseApi(private val client: HttpClient) {
    suspend fun create(createCourseRequest: CreateCourseRequest): ResponseResult<CourseResponse> {
        return client.post("/courses") {
            contentType(ContentType.Application.Json)
            setBody(createCourseRequest)
        }.toResult()
    }

    suspend fun update(createCourseRequest: CreateCourseRequest): ResponseResult<CourseResponse> {
        return client.post("/courses") {
            contentType(ContentType.Application.Json)
            setBody(createCourseRequest)
        }.toResult()
    }

    suspend fun getById(courseId: UUID): ResponseResult<CourseResponse> {
        return client.get("/courses/$courseId").toResult()
    }

    suspend fun getAll(): ResponseResult<List<CourseResponse>> {
        return client.get("/courses").toResult()
    }

     suspend fun getUpdates(onUpdate:suspend () -> Unit) {
        client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/updates/courses") {
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    onUpdate()
                }
            }
        }
    }
}