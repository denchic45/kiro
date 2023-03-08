package com.denchic45.kiro.di

import com.denchic45.kiro.preferences.AppPreferences
import com.denchic45.kiro.service.AuthService

abstract class AppComponent {
    abstract val apiComponent: ApiComponent
    abstract val preferencesComponent: PreferencesComponent

    abstract val appPreferences: AppPreferences

    abstract val authService: AuthService
}