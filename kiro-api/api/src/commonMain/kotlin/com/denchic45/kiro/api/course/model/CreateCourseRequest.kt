package com.kiro.api.course.model

import com.kiro.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreateCourseRequest(
    val name: String,
    val plannedHours: Int,
    val plannedStudentsCount: Int
)