package com.denchic45.kiro.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(component: AuthComponent) {
    val uiState by component.uiState.collectAsState()
    AuthPreview(uiState, component::onEmailType, component::onPasswordType, component::onSignInClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthPreview(
    uiState: AuthUiState,
    onEmailType: (String) -> Unit,
    onPasswordType: (String) -> Unit,
    onSignInClick: () -> Unit,
) {
    Column(
        Modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = uiState.email, onValueChange = { onEmailType(it) })
        Spacer(Modifier.height(12.dp))
        TextField(value = uiState.password, onValueChange = { onPasswordType(it) })
        Spacer(Modifier.height(12.dp))
        Button(onClick = { onSignInClick() }) {
            Text("Войти")
        }
    }
}

data class AuthUiState(
    val email: String,
    val password: String,
    val isLoading: Boolean,
    val error: String?,
)