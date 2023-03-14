package com.denchic45.kiro.repository

import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.common.toResource
import com.kiro.api.studygroup.StudyGroupApi
import com.kiro.api.studygroup.model.StudyGroupResponse
import com.kiro.common.ResponseResult
import me.tatarka.inject.annotations.Inject
import java.util.*

@Inject
class StudyGroupRepository(
    private val studyGroupApi: StudyGroupApi,
) {

    suspend fun findAll(): Resource<List<StudyGroupResponse>> {
        return studyGroupApi.getAll().toResource()
    }

    suspend fun findByCourseId(courseId: UUID): Resource<List<StudyGroupResponse>> {
        return studyGroupApi.getByCourseId(courseId).toResource()
    }

    suspend fun findById(id: UUID): Resource<StudyGroupResponse> {
        return studyGroupApi.getById(id).toResource()
    }
}