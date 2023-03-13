package com.kiro.feature.auth

import com.kiro.feature.auth.usecase.RefreshTokenUseCase
import com.kiro.feature.auth.usecase.SignUpUseCase
import com.kiro.feature.auth.usecase.SignInByEmailAndPasswordUseCase
import com.kiro.feature.auth.usecase.SignUpUserManuallyUseCase
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