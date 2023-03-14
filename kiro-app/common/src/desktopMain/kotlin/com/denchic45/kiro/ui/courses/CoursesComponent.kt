package com.denchic45.kiro.ui.courses

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.common.onSuccess
import com.denchic45.kiro.repository.CourseRepository
import com.denchic45.kiro.repository.StudyGroupRepository
import com.denchic45.kiro.ui.courseDetails.CourseDetailsComponent
import com.denchic45.kiro.util.componentScope
import com.kiro.api.course.model.CourseResponse
import com.kiro.api.studygroup.model.StudyGroupResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.*

@Inject
class CoursesComponent(
    private val courseRepository: CourseRepository,
    private val studyGroupRepository: StudyGroupRepository,
    private val _courseDetailsComponent: (UUID, ComponentContext) -> CourseDetailsComponent,
    @Assisted
    private val componentContext: ComponentContext,
) : ComponentContext by componentContext {


    private val componentScope = componentScope()

    val courses: StateFlow<Resource<List<CourseResponse>>> = flow {
        emitAll(courseRepository.findAllCourses())
    }.stateIn(
        componentScope,
        SharingStarted.Lazily,
        Resource.Loading
    )

    val selectedCourse = MutableStateFlow<UUID?>(null)
    val group = MutableStateFlow<StudyGroupResponse?>(null)

    val courseDetailsComponent: CourseDetailsComponent
        get() = _courseDetailsComponent(selectedCourse.value!!, componentContext)

    fun onCourseClick(id: UUID) {
        selectedCourse.value = id
    }

    fun onDetailsDismiss() {
        selectedCourse.update { null }
    }

    fun onGroupClick(id: UUID) {
        componentScope.launch {
            studyGroupRepository.findById(id).onSuccess {
                group.update { it }
            }
        }
    }
}