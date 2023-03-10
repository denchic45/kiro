package com.denchic45.kiro.feature.studygroup

import com.denchic45.kiro.api.studygroup.model.CreateStudyGroupRequest
import com.denchic45.kiro.api.studygroup.model.StudyGroupResponse
import com.denchic45.kiro.database.table.StudyGroupDao
import com.denchic45.kiro.database.table.StudyGroups
import com.studiversity.database.exists
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import java.util.*

class StudyGroupRepository {

    fun add(request: CreateStudyGroupRequest, courseId: UUID): StudyGroupResponse {
        val dao = StudyGroupDao.new {
            name = request.name
            studentsCount = request.studentsCount
            this.courseId = courseId
            studyDate = request.studyDate
            educationForm = request.educationForm
        }
        return dao.toResponse()
    }

    fun update(id: UUID, request: CreateStudyGroupRequest): StudyGroupDao {
        return StudyGroupDao.findById(id)?.apply {
            name = request.name
            studentsCount = request.studentsCount
            this.courseId = courseId
            studyDate = request.studyDate
            educationForm = request.educationForm
        }!!
    }

    fun findById(id: UUID): StudyGroupResponse? = StudyGroupDao.findById(id)?.toResponse()

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