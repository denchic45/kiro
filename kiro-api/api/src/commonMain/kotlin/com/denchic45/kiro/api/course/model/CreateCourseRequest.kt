package com.denchic45.kiro.api.course.model

import com.denchic45.kiro.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreateCourseRequest(
    val name: String,
    val plannedHours: Int,
    val plannedStudentsCount: Int
)