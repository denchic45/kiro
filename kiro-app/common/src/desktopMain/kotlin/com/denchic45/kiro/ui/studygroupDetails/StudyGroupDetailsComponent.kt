package com.denchic45.kiro.ui.studygroupDetails

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.common.Resource
import com.denchic45.kiro.repository.StudyGroupRepository
import com.denchic45.kiro.util.componentScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.*

@Inject
class StudyGroupDetailsComponent(
    private val studyGroupRepository: StudyGroupRepository,
    @Assisted
    groupId: UUID,
    @Assisted
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val componentScope = componentScope()

     val details = flow {
        emit(studyGroupRepository.findById(groupId))
    }.stateIn(componentScope, SharingStarted.Lazily, Resource.Loading)

}