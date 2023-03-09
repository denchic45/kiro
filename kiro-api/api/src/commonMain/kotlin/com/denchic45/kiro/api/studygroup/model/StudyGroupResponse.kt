package com.denchic45.kiro.api.studygroup.model

import com.denchic45.kiro.util.LocalDateSerializer
import com.denchic45.kiro.util.UUIDSerializer
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
    val studyDate: LocalDate,
    val educationForm: EducationForm
)
