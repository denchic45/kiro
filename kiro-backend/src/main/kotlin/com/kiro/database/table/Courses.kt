package com.kiro.database.table

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Courses : UUIDTable("course", "course_id") {
    val name = text("course_name")
    val plannedHours = integer("planned_hours")
    val plannedStudentsCount = integer("planned_students_count")
}

class CourseDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CourseDao>(Courses)

    var name by Courses.name
    var plannedHours by Courses.plannedHours
    var plannedStudentsCount by Courses.plannedStudentsCount

    val groups by StudyGroupDao referrersOn StudyGroups.id
}