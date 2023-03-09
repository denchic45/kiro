package com.denchic45.kiro.feature.course.usecase

import com.denchic45.kiro.feature.course.CourseRepository
import com.studiversity.transaction.TransactionWorker
import java.util.*

class FindCoursesUseCase(
    private val transactionWorker: TransactionWorker,
    private val courseRepository: CourseRepository
) {
    operator fun invoke() = transactionWorker {
        courseRepository.findAll()
    }
}