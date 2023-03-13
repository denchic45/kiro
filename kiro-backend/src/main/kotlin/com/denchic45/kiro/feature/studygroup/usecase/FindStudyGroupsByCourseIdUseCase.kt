package com.denchic45.kiro.feature.studygroup.usecase

import com.denchic45.kiro.feature.studygroup.StudyGroupRepository
import com.denchic45.kiro.transaction.TransactionWorker
import java.util.*

class FindStudyGroupsByCourseIdUseCase(
    private val transactionWorker: TransactionWorker,
    private val studyGroupRepository: StudyGroupRepository
) {
    operator fun invoke(id: UUID) = transactionWorker {
        studyGroupRepository.findByCourseId(id)
    }
}