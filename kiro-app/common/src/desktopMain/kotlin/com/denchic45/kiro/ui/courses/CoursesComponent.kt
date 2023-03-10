package com.denchic45.kiro.ui.courses

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.repository.CourseRepository
import com.denchic45.kiro.util.componentScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.*

@Inject
class CoursesComponent(
    private val courseRepository: CourseRepository,
    @Assisted
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val componentScope = componentScope()

    val courses: StateFlow<Resource<List<CourseResponse>>> = flow {
//        emit(courseRepository.findAllCourses())
        emit(
            Resource.Success(
                listOf(
                    CourseResponse(
                        UUID.randomUUID(),
                        "История",
                        64,
                        30,
                        emptyList()
                    ),
                    CourseResponse(
                        UUID.randomUUID(),
                        "Теория алгоритмов",
                        132,
                        54,
                        emptyList()
                    )
                )
            )
        )
    }.stateIn(
        componentScope,
        SharingStarted.Lazily,
        Resource.Loading
    )
}