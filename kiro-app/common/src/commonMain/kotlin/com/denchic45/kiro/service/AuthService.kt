package com.denchic45.kiro.service

import com.denchic45.kiro.api.auth.AuthApi
import com.denchic45.kiro.preferences.AppPreferences
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class AuthService(private val authApi: AuthApi, appPreferences: AppPreferences) {

    val isAuthed  = appPreferences.tokenFlow.map { it != null }
}