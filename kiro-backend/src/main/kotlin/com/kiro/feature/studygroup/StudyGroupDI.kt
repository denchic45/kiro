package com.kiro.feature.studygroup

import com.kiro.feature.studygroup.usecase.*
import com.kiro.feature.studygroup.usecase.RequireExistStudyGroupUseCase
import org.koin.dsl.module

private val useCaseModule = module {
    single { AddStudyGroupUseCase(get(), get()) }
    single { FindStudyGroupByIdUseCase(get(), get()) }
    single { FindAllStudyGroupsUseCase(get(),get()) }
    single { UpdateStudyGroupUseCase(get()) }
    single { RemoveStudyGroupUseCase(get(), get()) }
    single { RequireExistStudyGroupUseCase(get(), get()) }
    single { FindStudyGroupsByCourseIdUseCase(get(), get()) }
}

private val repositoryModule = module {
    single { StudyGroupRepository() }
}

val studyGroupModule = module {
    includes(useCaseModule, repositoryModule)
}