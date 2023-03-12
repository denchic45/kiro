package com.denchic45.kiro.feature.studygroup

import com.denchic45.kiro.api.studygroup.model.StudyGroupResponse
import com.denchic45.kiro.database.table.StudyGroupDao

fun StudyGroupDao.toResponse() = StudyGroupResponse(
    id = id.value,
    name = name,
    studentsCount = studentsCount,
    startStudyDate = startStudyDate,
    endStudyDate = endStudyDate,
    educationForm = educationForm
)
