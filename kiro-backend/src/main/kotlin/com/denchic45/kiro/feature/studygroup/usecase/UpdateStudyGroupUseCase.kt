package com.denchic45.kiro.feature.studygroup.usecase

import com.denchic45.kiro.api.studygroup.model.CreateStudyGroupRequest
import com.denchic45.kiro.feature.studygroup.repository.StudyGroupRepository
import java.util.*

class UpdateStudyGroupUseCase(private val groupRepository: StudyGroupRepository) {
    operator fun invoke(id: UUID, request: CreateStudyGroupRequest) {
        groupRepository.update(id, request)
    }
}