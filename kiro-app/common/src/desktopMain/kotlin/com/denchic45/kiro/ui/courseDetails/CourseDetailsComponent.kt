package com.denchic45.kiro.ui.courseDetails

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.repository.CourseRepository
import com.denchic45.kiro.repository.StudyGroupRepository
import com.denchic45.kiro.util.componentScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.*

@Inject
class CourseDetailsComponent(
    private val studyGroupRepository: StudyGroupRepository,
    private val courseRepository: CourseRepository,
    @Assisted
    private val courseId: UUID,
    @Assisted
    private val componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val componentScope = componentScope()

    val course = flow {
        emit(courseRepository.findById(courseId))
    }.stateIn(componentScope, SharingStarted.Lazily, Resource.Loading)

    val groups = flow {
        emit(studyGroupRepository.findByCourseId(courseId))
    }.stateIn(componentScope, SharingStarted.Lazily, Resource.Loading)

}