package com.denchic45.kiro.service

import com.denchic45.kiro.preferences.AppPreferences
import com.kiro.api.auth.AuthApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject

@Inject
class AuthService(private val authApi: AuthApi, appPreferences: AppPreferences, coroutineScope: CoroutineScope) {

    val isAuthed = appPreferences.tokenFlow.map { it != null }
        .stateIn(coroutineScope, SharingStarted.Lazily, appPreferences.token != null)
}