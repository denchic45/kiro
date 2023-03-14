package com.kiro.database.table

import com.kiro.api.studygroup.model.EducationForm
import com.kiro.util.varcharMax
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
    val courseId = reference(
        "course_id", Courses,
        onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE
    )
    val startStudyDate = date("start_study_date")
    val endStudyDate = date("end_study_date")
    val educationForm = enumerationByName<EducationForm>("education_form", 40)
}

class StudyGroupDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StudyGroupDao>(StudyGroups)

    var name by StudyGroups.name
    var studentsCount by StudyGroups.studentCount
    var course by CourseDao referencedOn StudyGroups.courseId
    var startStudyDate by StudyGroups.startStudyDate
    var endStudyDate by StudyGroups.endStudyDate
    var educationForm by StudyGroups.educationForm
}