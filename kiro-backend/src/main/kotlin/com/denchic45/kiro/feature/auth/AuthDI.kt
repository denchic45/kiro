package com.denchic45.kiro.feature.auth

import com.denchic45.kiro.feature.auth.usecase.RefreshTokenUseCase
import com.denchic45.kiro.feature.auth.usecase.SignUpUseCase
import com.denchic45.kiro.feature.auth.usecase.SignInByEmailAndPasswordUseCase
import com.denchic45.kiro.feature.auth.usecase.SignUpUserManuallyUseCase
import org.koin.dsl.module

private val useCaseModule = module {
    single { SignUpUseCase(get(), get()) }
    single { SignUpUserManuallyUseCase(get(), get(), get()) }
    single { SignInByEmailAndPasswordUseCase(get(), get()) }
    single { RefreshTokenUseCase(get(), get()) }
}

val authModule = module {
    includes(useCaseModule)
}