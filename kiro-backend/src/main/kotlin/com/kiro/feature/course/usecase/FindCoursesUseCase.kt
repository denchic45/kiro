package com.kiro.feature.course.usecase

import com.kiro.feature.course.CourseRepository
import com.kiro.transaction.TransactionWorker

class FindCoursesUseCase(
    private val transactionWorker: TransactionWorker,
    private val courseRepository: CourseRepository
) {
    operator fun invoke() = transactionWorker {
        courseRepository.findAll()
    }
}