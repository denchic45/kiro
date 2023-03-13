package com.kiro.feature.course

import com.kiro.api.course.model.CourseResponse
import com.kiro.api.course.model.CreateCourseRequest
import com.kiro.database.table.CourseDao
import com.kiro.database.table.Courses
import com.kiro.database.exists
import java.util.*

class CourseRepository {

    fun add(request: CreateCourseRequest): CourseResponse {
        val dao = CourseDao.new {
            name = request.name
            plannedHours = request.plannedHours
            plannedStudentsCount = request.plannedStudentsCount
        }
        return dao.toResponse()
    }

    fun findById(id: UUID): CourseResponse? {
        return CourseDao.findById(id)?.toResponse()
    }

//    fun find(q: String?, memberId: UUID?): List<CourseResponse> {
//        val query = Courses.selectAll()
//        q?.let {
//            query.adjustColumnSet { leftJoin(Subjects, { Courses.subjectId }, { Subjects.id }) }
//                .adjustSlice { slice(fields + Subjects.id) }
//                .andWhere {
//                    Courses.name.lowerCase() like "%$q%" or
//                            (Subjects.name.lowerCase() like "%$q%") or
//                            (Subjects.shortname.lowerCase() like "%$q%")
//                }
//        }
//        memberId?.let {
//            query.adjustColumnSet {
//                innerJoin(MembershipsInnerUserMemberships,
//                    { Courses.id },
//                    { Memberships.scopeId })
//            }.andWhere { UsersRolesScopes.userId eq memberId }
//        }
//        return CourseDao.wrapRows(query).map(CourseDao::toResponse)
//    }

    fun findAll(): List<CourseResponse> {
        return CourseDao.all().map(CourseDao::toResponse)
    }

    fun update(id: UUID, request: CreateCourseRequest): CourseResponse? = CourseDao.findById(id)?.apply {
        name = request.name
    }?.toResponse()


    fun exist(id: UUID): Boolean {
        return Courses.exists { Courses.id eq id }
    }

    fun removeCourse(courseId: UUID) {
        CourseDao.findById(courseId)!!.delete()
    }
}