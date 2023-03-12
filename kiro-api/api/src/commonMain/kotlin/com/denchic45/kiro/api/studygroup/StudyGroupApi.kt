package com.denchic45.kiro.api.studygroup

import com.denchic45.kiro.api.studygroup.model.CreateStudyGroupRequest
import com.denchic45.kiro.api.studygroup.model.StudyGroupResponse
import com.denchic45.kiro.common.ResponseResult
import com.denchic45.kiro.common.toResult
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.util.*

class StudyGroupApi(private val client: HttpClient) {
    suspend fun create(
        courseId: UUID,
        createStudyGroupRequest: CreateStudyGroupRequest
    ): ResponseResult<StudyGroupResponse> {
        return client.post("/courses/$courseId/studygroups") {
            contentType(ContentType.Application.Json)
            setBody(createStudyGroupRequest)
        }.toResult()
    }

    suspend fun update(
        studyGroupId: UUID,
        createStudyGroupRequest: CreateStudyGroupRequest
    ): ResponseResult<StudyGroupResponse> {
        return client.post("/studygroups/$studyGroupId") {
            contentType(ContentType.Application.Json)
            setBody(createStudyGroupRequest)
        }.toResult()
    }

    suspend fun getById(studyGroupId: UUID): ResponseResult<StudyGroupResponse> {
        return client.get("/studygroups/$studyGroupId").toResult()
    }

    suspend fun getByCourseId(courseId: UUID): ResponseResult<List<StudyGroupResponse>> {
        return client.get("/courses/$courseId/studygroups").toResult()
    }

    suspend fun delete(studyGroupId: UUID) {
        TODO()
    }

    suspend fun getAll(): ResponseResult<List<StudyGroupResponse>> {
        return client.get("/studygroups").toResult()
    }
}