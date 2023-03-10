package com.denchic45.kiro.repository

import com.denchic45.kiro.api.course.CourseApi
import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.common.toResource
import me.tatarka.inject.annotations.Inject

@Inject
class CourseRepository(private val courseApi: CourseApi) {

    suspend fun findAllCourses(): Resource<List<CourseResponse>> {
        return courseApi.getAll().toResource()
    }
}