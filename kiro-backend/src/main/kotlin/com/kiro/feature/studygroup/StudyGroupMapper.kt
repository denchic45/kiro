package com.kiro.feature.studygroup

import com.kiro.api.studygroup.model.StudyGroupResponse
import com.kiro.database.table.StudyGroupDao

fun StudyGroupDao.toResponse() = StudyGroupResponse(
    id = id.value,
    name = name,
    studentsCount = studentsCount,
    startStudyDate = startStudyDate,
    endStudyDate = endStudyDate,
    educationForm = educationForm
)
