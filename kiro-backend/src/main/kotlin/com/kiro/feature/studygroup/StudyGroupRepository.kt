package com.kiro.feature.studygroup

import com.kiro.api.studygroup.model.CreateStudyGroupRequest
import com.kiro.api.studygroup.model.StudyGroupResponse
import com.kiro.database.table.StudyGroupDao
import com.kiro.database.table.StudyGroups
import com.kiro.database.exists
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import java.util.*

class StudyGroupRepository {

    fun add(request: CreateStudyGroupRequest, courseId: UUID): StudyGroupResponse {
        val dao = StudyGroupDao.new {
            name = request.name
            studentsCount = request.studentsCount
            this.courseId = courseId
            startStudyDate = request.startStudyDate
            endStudyDate = request.endStudyDate
            educationForm = request.educationForm
        }
        return dao.toResponse()
    }

    fun update(id: UUID, request: CreateStudyGroupRequest): StudyGroupDao {
        return StudyGroupDao.findById(id)?.apply {
            name = request.name
            studentsCount = request.studentsCount
            this.courseId = courseId
            startStudyDate = request.startStudyDate
            endStudyDate = request.endStudyDate
            educationForm = request.educationForm
        }!!
    }

    fun findById(id: UUID): StudyGroupResponse? = StudyGroupDao.findById(id)?.toResponse()

    fun findByCourseId(courseId: UUID): List<StudyGroupResponse> {
        return StudyGroupDao.find(StudyGroups.courseId eq courseId).map(StudyGroupDao::toResponse)
    }

//    fun find(query: String) = StudyGroupDao.wrapRows(
//        StudyGroups.leftJoin(Specialties, { specialtyId }, { Specialties.id })
//            .select(
//                StudyGroups.name.lowerCase().trim() like "%$query%"
//                        or (Specialties.name.lowerCase().trim() like "%$query%")
//                        or (Specialties.shortname.lowerCase().trim() like "%$query%")
//            )
//    ).map(StudyGroupDao::toResponse)

    fun remove(id: UUID) = StudyGroups.deleteWhere { StudyGroups.id eq id }.run { this != 0 }

    fun exist(id: UUID): Boolean {
        return StudyGroups.exists { StudyGroups.id eq id }
    }
}