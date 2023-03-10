package com.denchic45.kiro.ui.courses

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Groups2
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.denchic45.kiro.api.course.model.CourseResponse
import com.denchic45.kiro.common.onSuccess
import com.denchic45.kiro.ui.theme.spacing
import java.util.*

@Composable
fun CoursesScreen(component: CoursesComponent) {
    val courses by component.courses.collectAsState()
    courses.onSuccess {
        LazyColumn {
            items(it) {
                CourseListItem(
                    response = it,
                    modifier = Modifier.padding(MaterialTheme.spacing.normal),
                    onClick = {},
                    onClickAction = {},
                    onDismissAction = {}
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseListItem(
    response: CourseResponse,
    modifier: Modifier = Modifier,
    onClick: (UUID) -> Unit,
    onClickAction: (String) -> Unit,
    onDismissAction: () -> Unit,
) {
    Card(onClick = { onClick(response.id) }, modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = response.name,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.weight(1f))
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Timer, null, Modifier.size(28.dp))
                Text(
                    text = "${response.plannedHours} час.",
                    modifier = Modifier.padding(start = MaterialTheme.spacing.small),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.width(24.dp))
                Icon(Icons.Outlined.Groups2, null, Modifier.size(28.dp))
                Text(
                    text = "${response.plannedHours} чел.",
                    modifier = Modifier.padding(start = MaterialTheme.spacing.small),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton({}) {
                Icon(Icons.Default.MoreVert, null)
            }

            var expanded by remember { mutableStateOf(false) }
            DropdownMenu(expanded = expanded,
                modifier = Modifier.width(240.dp),
                onDismissRequest = {
                    expanded = false
                    onDismissAction()
                }) {

                listOf("Удалить", "Редактировать").forEach { action ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onClickAction(action)
                        },
                        text = {
                            Text(
                                text = action,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        })
                }
            }
        }
    }
}

@Preview
@Composable
fun CourseListItemPreview() {
    CourseListItem(
        response = CourseResponse(
            UUID.randomUUID(),
            "Теория алгоритмов",
            132,
            54,
            emptyList()
        ),
        onClick = {},
        onClickAction = {},
        onDismissAction = {}
    )
}