package com.kiro.api.studygroup.model

import com.kiro.util.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CreateStudyGroupRequest(
    val name: String,
    val studentsCount: Int,
    @Serializable(LocalDateSerializer::class)
    val startStudyDate: LocalDate,
    @Serializable(LocalDateSerializer::class)
    val endStudyDate: LocalDate,
    val educationForm: EducationForm
)
