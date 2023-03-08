package com.denchic45.kiro.di

import com.denchic45.kiro.preferences.SettingsFactory
import io.ktor.client.engine.cio.*
import me.tatarka.inject.annotations.Component

val appComponent:DesktopAppComponent = DesktopAppComponent::class.create(
    apiComponent = ApiComponent::class.create(CIO),
    preferencesComponent = PreferencesComponent::class.create(SettingsFactory())
)


@Component
abstract class DesktopAppComponent(
    @Component override val apiComponent: ApiComponent,
    @Component override val preferencesComponent: PreferencesComponent
) : AppComponent()