package com.denchic45.kiro.ui.studygroups

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.repository.StudyGroupRepository
import com.denchic45.kiro.ui.studygroupDetails.StudyGroupDetailsComponent
import com.denchic45.kiro.util.componentScope
import kotlinx.coroutines.flow.*
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.*

@Inject
class StudyGroupsComponent(
    private val studyGroupRepository: StudyGroupRepository,
    private val _studyGroupDetailsComponent: (UUID, ComponentContext) -> StudyGroupDetailsComponent,
    @Assisted
    private val componentContext: ComponentContext,
) : ComponentContext by componentContext {
    private val componentScope = componentScope()

    val groups = flow {
        emit(studyGroupRepository.findAll())
    }.stateIn(
        componentScope,
        SharingStarted.Lazily, Resource.Loading
    )

    val selectedGroup = MutableStateFlow<UUID?>(null)

    val studyGroupDetailsComponent: StudyGroupDetailsComponent
        get() = _studyGroupDetailsComponent(selectedGroup.value!!, componentContext)

    fun onGroupClick(id: UUID) {
        selectedGroup.value = id
    }

    fun onDetailsDismiss() {
        selectedGroup.update { null }
    }
}