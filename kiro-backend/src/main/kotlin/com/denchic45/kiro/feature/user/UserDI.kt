package com.denchic45.kiro.feature.user

import com.denchic45.kiro.feature.user.usecase.FindUserByIdUseCase
import com.studiversity.feature.user.usecase.RemoveUserUseCase
import com.studiversity.feature.user.usecase.SearchUsersUseCase
import org.koin.dsl.module

private val useCaseModule = module {
    single { FindUserByIdUseCase(get(), get()) }
    single { RemoveUserUseCase(get(), get()) }
    single { SearchUsersUseCase(get(), get()) }
}

private val repositoryModule = module {
    single { UserRepository() }
}

val userModule = module {
    includes(useCaseModule, repositoryModule)
}