package com.denchic45.kiro.feature.course

import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.database.table.CourseDao
import com.denchic45.kiro.database.table.StudyGroupDao
import com.denchic45.kiro.feature.studygroup.toResponse

fun CourseDao.toResponse(): CourseResponse = CourseResponse(
    id = id.value,
    name = name,
    plannedHours = plannedHours,
    plannedStudents = plannedStudentsCount,
    groups = groups.map(StudyGroupDao::toResponse)
)