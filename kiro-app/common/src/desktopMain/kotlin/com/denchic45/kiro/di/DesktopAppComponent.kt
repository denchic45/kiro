package com.denchic45.kiro.di


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.denchic45.kiro.preferences.AppPreferences
import com.denchic45.kiro.preferences.SettingsFactory
import com.denchic45.kiro.ui.main.MainComponent
import io.ktor.client.engine.cio.*
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

val appComponent: DesktopAppComponent = DesktopAppComponent::class.create(
    apiComponent = ApiComponent::class.create(CIO),
    preferencesComponent = PreferencesComponent::class.create(SettingsFactory()),
    coroutineComponent = CoroutineComponent::class.create()
)

@AppScope
@Component
abstract class DesktopAppComponent(
    @Component override val preferencesComponent: PreferencesComponent,
    @Component override val apiComponent: ApiComponent,
    @Component override val coroutineComponent: CoroutineComponent
) : AppComponent() {

    abstract override val appPreferences: AppPreferences
    abstract val mainComponent: MainComponent
    abstract val componentContext:ComponentContext


    @AppScope
    @Provides
    fun componentContext(): ComponentContext = DefaultComponentContext(LifecycleRegistry())

}