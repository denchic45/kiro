package com.kiro.feature.studygroup.usecase

import com.kiro.feature.studygroup.StudyGroupRepository
import com.kiro.transaction.TransactionWorker
import io.ktor.server.plugins.*
import java.util.*

class RemoveStudyGroupUseCase(
    private val transactionWorker: TransactionWorker,
    private val groupRepository: StudyGroupRepository,
) {
    operator fun invoke(groupId: UUID) = transactionWorker {
        groupRepository.remove(groupId).apply {
            if (!this) throw NotFoundException()
        }
    }
}