package com.denchic45.kiro.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.denchic45.kiro.ui.theme.spacing

@Composable
fun AuthScreen(component: AuthComponent) {
    val uiState by component.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AuthUI(uiState, component::onEmailType, component::onPasswordType, component::onSignInClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthUI(
    uiState: AuthUiState,
    onEmailType: (String) -> Unit,
    onPasswordType: (String) -> Unit,
    onSignInClick: () -> Unit,
) {
    Column(
        Modifier.padding(horizontal = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Добро Пожаловать!", style = MaterialTheme.typography.titleLarge)
        Text("войдите в свою учетную запись", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(MaterialTheme.spacing.large))
        TextField(
            value = uiState.email,
            onValueChange = { onEmailType(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email") },
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        TextField(
            value = uiState.password,
            onValueChange = { onPasswordType(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            }
        )
        Spacer(Modifier.height(16.dp))
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