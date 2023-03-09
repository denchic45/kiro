package com.denchic45.kiro.feature.course.usecase

import com.denchic45.kiro.feature.course.CourseRepository
import com.studiversity.transaction.SuspendTransactionWorker
import io.ktor.server.plugins.*
import java.util.*

class RemoveCourseUseCase(
    private val transactionWorker: SuspendTransactionWorker,
    private val courseRepository: CourseRepository
) {
    suspend operator fun invoke(courseId: UUID) = transactionWorker.suspendInvoke {
        if (!courseRepository.exist(courseId))
            throw NotFoundException()
        courseRepository.removeCourse(courseId)
    }
}