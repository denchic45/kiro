package com.denchic45.kiro.ui.studygroups

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.api.studygroup.model.EducationForm
import com.denchic45.kiro.api.studygroup.model.StudyGroupResponse
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.common.onSuccess
import com.denchic45.kiro.repository.StudyGroupRepository
import com.denchic45.kiro.util.componentScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.time.LocalDate
import java.util.*

@Inject
class StudyGroupsComponent(
    private val studyGroupRepository: StudyGroupRepository,
    @Assisted componentContext: ComponentContext
) : ComponentContext by componentContext {
    private val componentScope = componentScope()
    private val studyGroupResponse = StudyGroupResponse(
        UUID.randomUUID(), "ПКС-4.2", 34, LocalDate.now(),
        LocalDate.now().plusMonths(2), EducationForm.EXTRAMURAL
    )

    val groups = flow {
        emit(
            Resource.Success(
                listOf(
                    studyGroupResponse,
                    StudyGroupResponse(
                        UUID.randomUUID(), "ПКС-4.2", 34, LocalDate.now(),
                        LocalDate.now().plusMonths(3), EducationForm.EXTRAMURAL
                    )
                )
            )
        )
//        emit(studyGroupsRepository.findAll())
    }.stateIn(
        componentScope,
        SharingStarted.Lazily, Resource.Loading
    )

    val selectedGroup = MutableStateFlow<StudyGroupResponse?>(null)

    fun onGroupClick(id: UUID) {
        componentScope.launch {
//            studyGroupsRepository.findById(id)
            Resource.Success(
                studyGroupResponse
            ).onSuccess { response ->
                selectedGroup.update { response }
            }
        }
    }

    fun onDetailsDismiss() {
        selectedGroup.update { null }
    }
}