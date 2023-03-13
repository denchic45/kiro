package com.kiro.feature.course

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

@Suppress("unused")
fun Application.configureCourses() {
    courseRoutes()
}

object CourseErrors {
    const val INVALID_COURSE_NAME = "INVALID_COURSE_NAME"
    const val STUDY_GROUP_ALREADY_EXIST = "STUDY_GROUP_ALREADY_EXIST"
    const val COURSE_IS_NOT_ARCHIVED = "COURSE_IS_NOT_ARCHIVED"
}