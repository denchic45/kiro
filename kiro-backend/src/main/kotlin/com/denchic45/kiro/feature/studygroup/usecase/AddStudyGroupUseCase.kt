package com.denchic45.kiro.feature.studygroup.usecase

import com.denchic45.kiro.api.studygroup.model.CreateStudyGroupRequest
import com.denchic45.kiro.api.studygroup.model.StudyGroupResponse
import com.denchic45.kiro.feature.studygroup.repository.StudyGroupRepository
import com.studiversity.transaction.TransactionWorker
import java.util.*

class AddStudyGroupUseCase(
    private val transactionWorker: TransactionWorker,
    private val studyGroupRepository: StudyGroupRepository
) {
    operator fun invoke(request: CreateStudyGroupRequest, courseId: UUID): StudyGroupResponse = transactionWorker {
        studyGroupRepository.add(request,courseId)
    }
}