package com.denchic45.kiro.api.studygroup.model

import java.time.LocalDate

data class CreateStudyGroupRequest(
    val name: String,
    val studentsCount: Int,
    val studyDate: LocalDate,
    val educationForm: EducationForm
)
