package com.kiro.feature.course.usecase

import com.kiro.api.course.model.CourseResponse
import com.kiro.api.course.model.CreateCourseRequest
import com.kiro.feature.course.CourseRepository
import io.ktor.server.plugins.*
import java.util.*

class UpdateCourseUseCase(private val courseRepository: CourseRepository) {
    operator fun invoke(id: UUID, request: CreateCourseRequest): CourseResponse {
        return courseRepository.update(id, request) ?: throw NotFoundException()
    }
}