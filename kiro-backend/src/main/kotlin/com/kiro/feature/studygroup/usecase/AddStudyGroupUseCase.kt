package com.kiro.feature.studygroup.usecase

import com.kiro.api.studygroup.model.CreateStudyGroupRequest
import com.kiro.api.studygroup.model.StudyGroupResponse
import com.kiro.feature.studygroup.StudyGroupRepository
import com.kiro.transaction.TransactionWorker
import java.util.*

class AddStudyGroupUseCase(
    private val transactionWorker: TransactionWorker,
    private val studyGroupRepository: StudyGroupRepository
) {
    operator fun invoke(request: CreateStudyGroupRequest, courseId: UUID): StudyGroupResponse =
        transactionWorker {
            studyGroupRepository.add(request,courseId)
        }
}