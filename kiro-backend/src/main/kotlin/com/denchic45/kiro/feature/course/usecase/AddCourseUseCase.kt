package com.denchic45.kiro.feature.course.usecase

import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.api.course.model.CreateCourseRequest
import com.denchic45.kiro.feature.course.CourseRepository
import com.studiversity.transaction.TransactionWorker

class AddCourseUseCase(
    private val transactionWorker: TransactionWorker,
    private val courseRepository: CourseRepository,
) {
    operator fun invoke(request: CreateCourseRequest): CourseResponse = transactionWorker {
        courseRepository.add(request)
    }
}