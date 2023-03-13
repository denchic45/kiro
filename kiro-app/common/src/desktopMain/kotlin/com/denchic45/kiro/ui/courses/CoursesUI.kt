package com.denchic45.kiro.ui.courses

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.denchic45.kiro.common.onSuccess
import com.denchic45.kiro.ui.component.HeaderItem
import com.denchic45.kiro.ui.theme.spacing
import com.kiro.api.course.model.CourseResponse
import com.kiro.api.studygroup.model.StudyGroupResponse
import java.util.*

@Composable
fun CoursesScreen(component: CoursesComponent) {
    val courses by component.courses.collectAsState()
    val details by component.selectedCourse.collectAsState()
    Row {
        courses.onSuccess {
            LazyColumn(Modifier.fillMaxHeight().weight(1f)) {
                items(it) {
                    CourseListItem(
                        response = it,
                        selected = details?.first?.id == it.id,
                        onClick = { component.onCourseClick(it) },
                        onClickAction = {}
                    ) {}
                    Spacer(Modifier.height(MaterialTheme.spacing.normal))
                }
            }
        }

        details?.let {
            Spacer(Modifier.width(MaterialTheme.spacing.normal))
            CourseDetails(
                it.first,
                it.second,
                { component.onGroupClick(it) }) { component.onDetailsDismiss() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseListItem(
    response: CourseResponse,
    selected: Boolean,
    onClick: (UUID) -> Unit,
    onClickAction: (String) -> Unit,
    onDismissAction: () -> Unit,
) {
    Card(
        onClick = { onClick(response.id) },
        colors = if (selected)
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            )
        else CardDefaults.cardColors()
    ) {
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
                    text = "${response.plannedStudents} чел.",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetails(
    response: CourseResponse,
    groups: List<StudyGroupResponse>,
    onGroupClick: (UUID) -> Unit,
    onDismissClick: () -> Unit,
) {
    Card(
        modifier = Modifier.width(296.dp).fillMaxHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(response.name, Modifier)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDismissClick() }, modifier = Modifier) {
                    Icon(
                        Icons.Default.Close,
                        null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Divider(Modifier.fillMaxWidth())
            ListItem(
                headlineText = { Text("${response.plannedStudents} чел.") },
                leadingContent = { Icon(Icons.Outlined.Group, null) },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )
            ListItem(
                headlineText = { Text("${response.plannedHours} час.") },
                leadingContent = { Icon(Icons.Outlined.Timer, null) },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )
            HeaderItem("Группы") {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
            LazyColumn {
                items(groups) {
                    CourseStudyGroupItem(it) { id -> onGroupClick(id) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseStudyGroupItem(response: StudyGroupResponse, onClick: (UUID) -> Unit) {
    ListItem(
        headlineText = { Text(response.name) },
        modifier = Modifier.clickable { onClick(response.id) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        leadingContent = { Icon(Icons.Outlined.Groups, null) },
        trailingContent = { Icon(Icons.Outlined.MoreVert, null) }
    )
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
        selected = false,
        onClick = {},
        onClickAction = {}
    ) {}
}