package com.denchic45.kiro.ui.studygroups

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.common.onSuccess
import com.denchic45.kiro.repository.StudyGroupRepository
import com.denchic45.kiro.util.componentScope
import com.kiro.api.studygroup.model.StudyGroupResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.*

@Inject
class StudyGroupsComponent(
    private val studyGroupRepository: StudyGroupRepository,
    @Assisted componentContext: ComponentContext,
) : ComponentContext by componentContext {
    private val componentScope = componentScope()

    val groups = flow {
        emit(studyGroupRepository.findAll())
    }.stateIn(
        componentScope,
        SharingStarted.Lazily, Resource.Loading
    )

    val selectedGroup = MutableStateFlow<StudyGroupResponse?>(null)

    fun onGroupClick(id: UUID) {
        componentScope.launch {
            studyGroupRepository.findById(id).onSuccess { response ->
                selectedGroup.update { response }
            }
        }
    }

    fun onDetailsDismiss() {
        selectedGroup.update { null }
    }
}