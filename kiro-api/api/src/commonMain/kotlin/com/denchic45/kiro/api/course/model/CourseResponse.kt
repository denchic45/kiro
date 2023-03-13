package com.kiro.api.course.model

import com.kiro.api.studygroup.model.StudyGroupResponse
import com.kiro.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CourseResponse(
    @Serializable(UUIDSerializer::class)
    val id:UUID,
    val name: String,
    val plannedHours: Int,
    val plannedStudents: Int,
    val groups: List<StudyGroupResponse>
)