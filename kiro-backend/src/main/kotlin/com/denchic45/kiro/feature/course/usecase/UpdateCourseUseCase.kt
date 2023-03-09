package com.denchic45.kiro.feature.course.usecase

import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.api.course.model.CreateCourseRequest
import com.denchic45.kiro.feature.course.CourseRepository
import io.ktor.server.plugins.*
import java.util.*

class UpdateCourseUseCase(private val courseRepository: CourseRepository) {
    operator fun invoke(id: UUID, request: CreateCourseRequest): CourseResponse {
        return courseRepository.update(id, request) ?: throw NotFoundException()
    }
}