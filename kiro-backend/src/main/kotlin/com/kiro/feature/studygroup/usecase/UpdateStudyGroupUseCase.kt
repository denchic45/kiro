package com.kiro.feature.studygroup.usecase

import com.kiro.api.studygroup.model.CreateStudyGroupRequest
import com.kiro.feature.studygroup.StudyGroupRepository
import java.util.*

class UpdateStudyGroupUseCase(private val groupRepository: StudyGroupRepository) {
    operator fun invoke(id: UUID, request: CreateStudyGroupRequest) {
        groupRepository.update(id, request)
    }
}