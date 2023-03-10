package com.denchic45.kiro.feature.studygroup.usecase

import com.denchic45.kiro.feature.studygroup.StudyGroupRepository
import com.studiversity.transaction.TransactionWorker
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