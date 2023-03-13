package com.denchic45.kiro.feature.course.usecase

import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.feature.course.CourseRepository
import com.denchic45.kiro.transaction.TransactionWorker
import io.ktor.server.plugins.*
import java.util.*

class FindCourseByIdUseCase(
    private val transactionWorker: TransactionWorker,
    private val courseRepository: CourseRepository
) {
    operator fun invoke(id: UUID): CourseResponse = transactionWorker {
        courseRepository.findById(id) ?: throw NotFoundException()
    }
}