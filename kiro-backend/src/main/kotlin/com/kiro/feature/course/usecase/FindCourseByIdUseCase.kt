package com.kiro.feature.course.usecase

import com.kiro.api.course.model.CourseResponse
import com.kiro.feature.course.CourseRepository
import com.kiro.transaction.TransactionWorker
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