package com.denchic45.kiro.api.course

import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.api.course.model.CreateCourseRequest
import com.denchic45.kiro.common.ResponseResult
import com.denchic45.kiro.common.toResult
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
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
}