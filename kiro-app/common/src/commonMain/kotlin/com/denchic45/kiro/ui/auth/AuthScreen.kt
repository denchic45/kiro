package com.denchic45.kiro.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(component: AuthComponent) {
    Column(Modifier.padding(horizontal = 24.dp)) {
        val email:String by component.email.collectAsState()
        TextField(value = email, onValueChange = {component.})
    }
}