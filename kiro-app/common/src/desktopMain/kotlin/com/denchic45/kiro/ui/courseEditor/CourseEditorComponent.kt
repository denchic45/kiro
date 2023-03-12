package com.denchic45.kiro.ui.courseEditor

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.api.course.model.CreateCourseRequest
import com.denchic45.kiro.repository.CourseRepository
import com.denchic45.kiro.util.componentScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class CourseEditorComponent(
    private val courseRepository: CourseRepository,
    @Assisted
    componentContext: ComponentContext,
    @Assisted
    private val onFinish: () -> Unit
) : ComponentContext by componentContext {

    private val componentScope = componentScope()
    val uiState = MutableStateFlow(UiState())
    val saveEnabled = uiState.map {
        with(it) {
            name.isNotEmpty()
                .and(plannedHours.isNotEmpty())
                .and(plannedStudentsCount.isNotEmpty())
        }
    }.stateIn(componentScope, SharingStarted.Lazily, false)

    fun onNameType(name: String) {
        uiState.update { it.copy(name = name) }
    }

    fun onHoursType(hours: String) {
        uiState.update { it.copy(plannedHours = hours) }
    }

    fun onStudentsCountType(studentsCount: String) {
        uiState.update { it.copy(plannedStudentsCount = studentsCount) }
    }

    fun onSaveCourse() {
        componentScope.launch {
            courseRepository.add(uiState.value.let { state ->
                CreateCourseRequest(
                    state.name,
                    state.plannedHours.toInt(),
                    state.plannedStudentsCount.toInt()
                )
            })
            onFinish()
        }
    }

    fun onCloseRequest() {
        onFinish()
    }

    data class UiState(
        val name: String = "",
        val plannedHours: String = "",
        val plannedStudentsCount: String = ""
    )
}

