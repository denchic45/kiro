package com.kiro.api.studygroup.model

import com.kiro.util.LocalDateSerializer
import com.kiro.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

@Serializable
data class StudyGroupResponse(
    @Serializable(UUIDSerializer::class)
    val id:UUID,
    val name: String,
    val studentsCount: Int,
    @Serializable(LocalDateSerializer::class)
    val startStudyDate: LocalDate,
    @Serializable(LocalDateSerializer::class)
    val endStudyDate:LocalDate,
    val educationForm: EducationForm
)
