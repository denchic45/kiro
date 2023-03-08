package com.denchic45.kiro.di

import com.denchic45.kiro.preferences.AppPreferences
import com.denchic45.kiro.preferences.UserPreferences
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import com.denchic45.kiro.preferences.SettingsFactory

@LayerScope
@Component
abstract class PreferencesComponent(@get:Provides val factory: SettingsFactory) {

    @LayerScope
    @Provides
    fun provideAppPreferences(): AppPreferences {
        return AppPreferences(factory.create("app"))
    }

    @LayerScope
    @Provides
    fun provideUserPreferences(): UserPreferences {
        return UserPreferences(factory.create("user"))
    }
}

