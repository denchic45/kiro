package com.kiro.feature.course.usecase

import com.kiro.api.course.model.CourseResponse
import com.kiro.api.course.model.CreateCourseRequest
import com.kiro.feature.course.CourseRepository
import com.kiro.transaction.TransactionWorker

class AddCourseUseCase(
    private val transactionWorker: TransactionWorker,
    private val courseRepository: CourseRepository,
) {
    operator fun invoke(request: CreateCourseRequest): CourseResponse = transactionWorker {
        courseRepository.add(request)
    }
}