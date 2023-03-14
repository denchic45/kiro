package com.denchic45.kiro.ui.auth


import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.preferences.AppPreferences
import com.denchic45.kiro.util.componentScope
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.kiro.api.auth.AuthApi
import com.kiro.api.auth.model.SignInByEmailPasswordRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class AuthComponent(
    private val authApi: AuthApi,
    private val appPreferences: AppPreferences,
    @Assisted
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    val uiState = MutableStateFlow(AuthUiState("", "", false, null))

    private val scope = componentScope()

    fun onEmailType(emailText: String) {
        uiState.update { it.copy(email = emailText) }
    }

    fun onPasswordType(passwordText: String) {
        uiState.update { it.copy(password = passwordText) }
    }

    fun onSignInClick() {
        scope.launch {
            val value = uiState.value
            uiState.update { it.copy(isLoading = true, error = null) }
            authApi.signInByEmailPassword(SignInByEmailPasswordRequest(value.email, value.password))
                .onSuccess { response ->
                    appPreferences.token = response.token
                    appPreferences.refreshToken = response.refreshToken
                }
                .onFailure { error ->
                    uiState.update { it.copy(isLoading = false, error = error.error.reason) }
                }
        }
    }
}