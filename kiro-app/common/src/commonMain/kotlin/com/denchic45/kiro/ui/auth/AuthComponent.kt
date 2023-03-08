package com.denchic45.kiro.ui.auth


import com.arkivanov.decompose.ComponentContext
import com.denchic45.kiro.api.auth.AuthApi
import com.denchic45.kiro.api.auth.model.SignInByEmailPasswordRequest
import com.denchic45.kiro.util.componentScope
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class AuthComponent(
    private val authApi: AuthApi,
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
                .onSuccess { }
                .onFailure { error ->
                    uiState.update { it.copy(isLoading = false, error = error.error.reason) }
                }
        }
    }
}