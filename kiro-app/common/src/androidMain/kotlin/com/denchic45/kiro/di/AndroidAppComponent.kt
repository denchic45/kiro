package com.denchic45.kiro.di

import me.tatarka.inject.annotations.Component

@Component
abstract class AndroidAppComponent(
    @Component override val preferencesComponent: PreferencesComponent,
    @Component override val apiComponent: ApiComponent,
    @Component override val coroutineComponent: CoroutineComponent
) : AppComponent()