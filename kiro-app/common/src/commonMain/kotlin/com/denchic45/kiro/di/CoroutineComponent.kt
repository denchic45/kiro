package com.denchic45.kiro.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides


@LayerScope
@Component
abstract class CoroutineComponent {

    @LayerScope
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}