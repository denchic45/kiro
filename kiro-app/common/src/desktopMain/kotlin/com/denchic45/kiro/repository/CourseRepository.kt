package com.denchic45.kiro.repository

import com.denchic45.kiro.api.course.CourseApi
import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.api.course.model.CreateCourseRequest
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.common.ResponseResult
import com.denchic45.kiro.common.toResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.tatarka.inject.annotations.Inject
import java.util.*

@Inject
class CourseRepository(private val courseApi: CourseApi) {

    fun findAllCourses(): Flow<Resource<List<CourseResponse>>> {
        return flow {
            courseApi.getUpdates {
                emit(courseApi.getAll().toResource())
            }
        }
    }

    suspend fun add(request: CreateCourseRequest): ResponseResult<CourseResponse> {
        return courseApi.create(request)
    }

    suspend fun findById(id: UUID): ResponseResult<CourseResponse> {
        return courseApi.getById(id)
    }
}