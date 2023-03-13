package com.kiro.feature.course

import com.kiro.api.course.model.CourseResponse
import com.kiro.database.table.CourseDao
import com.kiro.database.table.StudyGroupDao
import com.kiro.feature.studygroup.toResponse

fun CourseDao.toResponse(): CourseResponse = CourseResponse(
    id = id.value,
    name = name,
    plannedHours = plannedHours,
    plannedStudents = plannedStudentsCount,
    groups = groups.map(StudyGroupDao::toResponse)
)