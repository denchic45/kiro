package com.kiro.feature.studygroup.usecase

import com.kiro.feature.studygroup.StudyGroupRepository
import com.kiro.transaction.TransactionWorker
import java.util.*

class FindStudyGroupsByCourseIdUseCase(
    private val transactionWorker: TransactionWorker,
    private val studyGroupRepository: StudyGroupRepository
) {
    operator fun invoke(id: UUID) = transactionWorker {
        studyGroupRepository.findByCourseId(id)
    }
}