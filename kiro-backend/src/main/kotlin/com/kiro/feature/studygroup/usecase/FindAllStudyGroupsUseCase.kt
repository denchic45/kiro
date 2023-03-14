package com.kiro.feature.studygroup.usecase

import com.kiro.feature.studygroup.StudyGroupRepository
import com.kiro.transaction.TransactionWorker

class FindAllStudyGroupsUseCase(
    private val transactionWorker: TransactionWorker,
    private val studyGroupRepository: StudyGroupRepository
) {
    operator fun invoke() = transactionWorker {
        studyGroupRepository.findAll()
    }
}