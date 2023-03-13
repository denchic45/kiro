package com.kiro.feature.studygroup.usecase

import com.kiro.feature.studygroup.StudyGroupRepository
import com.kiro.transaction.TransactionWorker
import io.ktor.server.plugins.*
import java.util.*

class RequireExistStudyGroupUseCase(
    private val transactionWorker: TransactionWorker,
    private val studyGroupRepository: StudyGroupRepository
) {

    operator fun invoke(id: UUID) = transactionWorker {
        if (!studyGroupRepository.exist(id)) throw NotFoundException()
    }
}