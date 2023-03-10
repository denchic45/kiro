package com.denchic45.kiro.feature.studygroup.usecase

import com.denchic45.kiro.api.studygroup.model.StudyGroupResponse
import com.denchic45.kiro.feature.studygroup.StudyGroupRepository
import com.studiversity.transaction.TransactionWorker
import io.ktor.server.plugins.*
import java.util.*

class FindStudyGroupByIdUseCase(
    private val transactionWorker: TransactionWorker,
    private val studyGroupRepository: StudyGroupRepository
) {
    operator fun invoke(id: UUID): StudyGroupResponse = transactionWorker {
        studyGroupRepository.findById(id) ?: throw NotFoundException()
    }
}