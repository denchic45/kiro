package com.denchic45.kiro.database.table

import com.denchic45.kiro.api.studygroup.model.EducationForm
import com.studiversity.util.varcharMax
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

object StudyGroups : UUIDTable("study_group", "study_group_id") {
    val name = varcharMax("group_name")
    val studentCount = integer("study_count")
    val courseId = uuid("course_id").references(
        Courses.id, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE
    )
    val studyDate = date("study_date")
    val educationForm = enumerationByName<EducationForm>("education_form", 40)
}

class StudyGroupDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StudyGroupDao>(StudyGroups)

    var name by StudyGroups.name
    var studentsCount by StudyGroups.studentCount
    var courseId by StudyGroups.courseId
    var studyDate by StudyGroups.studyDate
    var educationForm by StudyGroups.educationForm
}