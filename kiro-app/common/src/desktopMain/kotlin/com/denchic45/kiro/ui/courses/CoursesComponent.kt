package com.denchic45.kiro.ui.courses

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.common.onSuccess
import com.denchic45.kiro.repository.CourseRepository
import com.denchic45.kiro.repository.StudyGroupRepository
import com.denchic45.kiro.util.componentScope
import com.github.michaelbull.result.onSuccess
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
    @Assisted
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val componentScope = componentScope()

    private val courseResponse = CourseResponse(
        UUID.randomUUID(),
        "История",
        64,
        30,
        emptyList()
    )

    val courses: StateFlow<Resource<List<CourseResponse>>> = flow {
        emitAll(courseRepository.findAllCourses())
//        emit(
//            Resource.Success(
//                listOf(
//                    courseResponse,
//                    CourseResponse(
//                        UUID.randomUUID(),
//                        "Теория алгоритмов",
//                        132,
//                        54,
//                        emptyList()
//                    )
//                )
//            )
//        )
    }.stateIn(
        componentScope,
        SharingStarted.Lazily,
        Resource.Loading
    )

    val selectedCourse = MutableStateFlow<Pair<CourseResponse, List<StudyGroupResponse>>?>(null)
    val group = MutableStateFlow<StudyGroupResponse?>(null)

    fun onCourseClick(id: UUID) {
        componentScope.launch {
            courseRepository.findById(id)
                .onSuccess { courseResponse ->
                    studyGroupRepository.findByCourseId(courseResponse.id)
//                Resource.Success(
//                    listOf(
//                        StudyGroupResponse(
//                            UUID.randomUUID(), "ПКС-4.2", 34, LocalDate.now(),
//                            LocalDate.now().plusMonths(3), EducationForm.EXTRAMURAL
//                        ),
//                        StudyGroupResponse(
//                            UUID.randomUUID(), "ИССА-3", 22, LocalDate.now(),
//                            LocalDate.now().plusWeeks(3).plusMonths(1), EducationForm.EXTRAMURAL
//                        )
//                    )
//                )
                        .onSuccess { groups ->
                            selectedCourse.update { courseResponse to groups }
                        }
                }
        }
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