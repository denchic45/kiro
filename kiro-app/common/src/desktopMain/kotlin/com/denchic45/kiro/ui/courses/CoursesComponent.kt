package com.denchic45.kiro.ui.courses

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.overlay.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
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

    private val sidebarNavigation = OverlayNavigation<CourseDetailsConfig>()

    val sidebarStack: Value<ChildOverlay<CourseDetailsConfig, CourseDetailsComponent>> =
        childOverlay(
            source = sidebarNavigation,
            handleBackButton = true,
        ) { config, componentContext ->
            _courseDetailsComponent(config.id, componentContext)
        }

    private val componentScope = componentScope()

    val courses: StateFlow<Resource<List<CourseResponse>>> = flow {
        emitAll(courseRepository.findAllCourses())
    }.stateIn(
        componentScope,
        SharingStarted.Lazily,
        Resource.Loading
    )

    @Parcelize
    data class CourseDetailsConfig(
        val id: UUID,
    ) : Parcelable

    val selectedCourse = MutableStateFlow<UUID?>(null)
    val group = MutableStateFlow<StudyGroupResponse?>(null)

    fun onCourseClick(id: UUID) {
        sidebarNavigation.activate(CourseDetailsConfig(id)) {
            selectedCourse.value = id
        }
    }

    fun onDetailsDismiss() {
        sidebarNavigation.dismiss {
            selectedCourse.update { null }
        }
    }

    fun onGroupClick(id: UUID) {
        componentScope.launch {
            studyGroupRepository.findById(id).onSuccess {
                group.update { it }
            }
        }
    }
}