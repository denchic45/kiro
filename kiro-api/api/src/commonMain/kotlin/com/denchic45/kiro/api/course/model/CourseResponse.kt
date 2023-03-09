package com.denchic45.kiro.api.course.model

import com.denchic45.kiro.api.studygroup.model.StudyGroupResponse
import com.denchic45.kiro.util.UUIDSerializer
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