package com.denchic45.kiro.ui.studygroups

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.overlay.OverlayNavigation
import com.arkivanov.decompose.router.overlay.activate
import com.arkivanov.decompose.router.overlay.childOverlay
import com.arkivanov.decompose.router.overlay.dismiss
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
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

    private val sidebarNavigation = OverlayNavigation<StudyGroupDetailsConfig>()

    val sidebarStack = childOverlay(
        source = sidebarNavigation,
        handleBackButton = true,
    ) { config, componentContext ->
        _studyGroupDetailsComponent(config.id, componentContext)
    }

    val groups = flow {
        emit(studyGroupRepository.findAll())
    }.stateIn(
        componentScope,
        SharingStarted.Lazily, Resource.Loading
    )

    val selectedGroup = MutableStateFlow<UUID?>(null)

    fun onGroupClick(id: UUID) {
        sidebarNavigation.activate(StudyGroupDetailsConfig(id)) {
            selectedGroup.value = id
        }
    }

    fun onDetailsDismiss() {
        sidebarNavigation.dismiss { selectedGroup.update { null } }
    }

    @Parcelize
    data class StudyGroupDetailsConfig(
        val id: UUID,
    ) : Parcelable
}