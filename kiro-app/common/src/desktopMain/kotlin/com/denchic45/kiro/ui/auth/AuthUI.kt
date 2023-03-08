package com.denchic45.kiro.ui.auth

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

@Preview
@Composable
fun AuthPreview() {
    AuthPreview(AuthUiState("qwerty1@gmail.com","",false,null),{},{},{})
}