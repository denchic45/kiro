package com.denchic45.kiro.feature.studygroup

import com.denchic45.kiro.feature.studygroup.repository.StudyGroupRepository
import com.denchic45.kiro.feature.studygroup.usecase.AddStudyGroupUseCase
import com.denchic45.kiro.feature.studygroup.usecase.FindStudyGroupByIdUseCase
import com.denchic45.kiro.feature.studygroup.usecase.RemoveStudyGroupUseCase
import com.denchic45.kiro.feature.studygroup.usecase.UpdateStudyGroupUseCase
import com.studiversity.feature.studygroup.repository.StudyGroupMemberRepository
import com.studiversity.feature.studygroup.usecase.*
import org.koin.dsl.module

private val useCaseModule = module {
    single { FindStudyGroupByIdUseCase(get(), get()) }
    single { AddStudyGroupUseCase(get(), get()) }
    single { UpdateStudyGroupUseCase(get()) }
    single { RemoveStudyGroupUseCase(get(), get()) }
    single { RequireExistStudyGroupUseCase(get(), get()) }
}

private val repositoryModule = module {
    single { StudyGroupRepository() }
    single { StudyGroupMemberRepository() }
}

val studyGroupModule = module {
    includes(useCaseModule, repositoryModule)
}