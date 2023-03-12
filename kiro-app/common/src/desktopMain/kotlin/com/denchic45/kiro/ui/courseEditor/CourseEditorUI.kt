package com.denchic45.kiro.ui.courseEditor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import com.denchic45.kiro.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseEditorUI(
    uiState: CourseEditorComponent.UiState,
    onNameType: (String) -> Unit,
    onHoursType: (String) -> Unit,
    onStudentsCountType: (String) -> Unit
) {
    val pattern = remember { Regex("^\\d+\$") }
    Column {
        TextField(
            value = uiState.name,
            onValueChange = { onNameType(it) },
            label = { Text("Название") }
        )
        Spacer(Modifier.height(MaterialTheme.spacing.normal))
        TextField(
            value = uiState.plannedHours,
            onValueChange = {
                if (it.isEmpty() || it.matches(pattern)) {
                    onHoursType(it)
                }
            },
            label = { Text("Планируемые часы") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(MaterialTheme.spacing.normal))
        TextField(
            value = uiState.plannedStudentsCount,
            onValueChange = {
                if (it.isEmpty() || it.matches(pattern)) {
                    onStudentsCountType(it)
                }
            },
            label = { Text("Планируемое кол-во учащихся") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(MaterialTheme.spacing.normal))
    }
}

@Composable
fun CourseEditorDialog(component: CourseEditorComponent) {
    Dialog(
        onCloseRequest = { component.onCloseRequest() },
        resizable = false,
        state = DialogState(width = 320.dp, height = 340.dp),
        title = "Создать курс"
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            val uiState by component.uiState.collectAsState()
            CourseEditorUI(
                uiState = uiState,
                onNameType = component::onNameType,
                onHoursType = component::onHoursType,
                onStudentsCountType = component::onStudentsCountType
            )
            val saveEnabled by component.saveEnabled.collectAsState()
            Spacer(Modifier.weight(1f))
            Row(Modifier.padding(horizontal = MaterialTheme.spacing.normal)) {
                Spacer(Modifier.weight(1f))
                Button(onClick = component::onSaveCourse, enabled = saveEnabled) {
                    Text("Сохранить")
                }
            }
        }
    }
}