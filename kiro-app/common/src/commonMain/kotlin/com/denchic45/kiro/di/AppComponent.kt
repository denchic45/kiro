package com.denchic45.kiro.di

import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.preferences.AppPreferences
import com.denchic45.kiro.service.AuthService
import com.denchic45.kiro.ui.auth.AuthComponent

abstract class AppComponent {
    abstract val coroutineComponent: CoroutineComponent
    abstract val appPreferences: AppPreferences
    abstract val preferencesComponent: PreferencesComponent
    abstract val authComponent: (ComponentContext)->AuthComponent
    abstract val apiComponent: ApiComponent

    abstract val authService: AuthService
}