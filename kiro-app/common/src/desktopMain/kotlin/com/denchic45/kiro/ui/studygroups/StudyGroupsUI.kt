package com.denchic45.kiro.ui.studygroups

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.denchic45.kiro.common.onSuccess
import com.kiro.api.studygroup.model.EducationForm
import com.kiro.api.studygroup.model.StudyGroupResponse
import com.denchic45.kiro.ui.theme.spacing
import com.kiro.util.toString
import java.time.LocalDate
import java.util.*

@Composable
fun StudyGroupsScreen(component: StudyGroupsComponent) {
    Row {
        val details by component.selectedGroup.collectAsState()
        Card(
            Modifier.fillMaxHeight().weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            val groups by component.groups.collectAsState()
            groups.onSuccess {
                LazyColumn(modifier = Modifier.padding(vertical = 16.dp)) {
                    items(it) {
                        StudyGroupListItem(
                            response = it,
                            selected = details?.id == it.id,
                            onClick = component::onGroupClick
                        )
                    }
                }
            }
        }

        details?.let {
            Spacer(Modifier.width(MaterialTheme.spacing.normal))
            StudyGroupDetails(it) {component.onDetailsDismiss()}
        }
    }
}

@Composable
fun StudyGroupListItem(
    response: StudyGroupResponse,
    selected: Boolean,
    interactionSource: MutableInteractionSource = remember(::MutableInteractionSource),
    onClick: (UUID) -> Unit
) {
    Row(
        Modifier.run {
            if (selected) background(MaterialTheme.colorScheme.secondaryContainer)
            else this
        }.selectable(
            selected = selected, interactionSource = interactionSource,
            indication = rememberRipple(color = MaterialTheme.colorScheme.secondary)
        ) { onClick(response.id) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Outlined.Group, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(response.name, Modifier.padding(start = 16.dp))
        Spacer(Modifier.weight(1f))
        Text("${response.studentsCount} чел.")
        Spacer(Modifier.weight(1f))
        Text(
            response.startStudyDate.toString("dd MMM YYY") + " - "
                    + response.endStudyDate.toString("dd MMM YYY")
        )
        Spacer(Modifier.weight(1f))
        Text(
            when (response.educationForm) {
                EducationForm.FULL_TIME -> "Очно"
                EducationForm.EXTRAMURAL -> "Заочно"
            }
        )
        Spacer(Modifier.weight(1f))
        IconButton({}) {
            Icon(Icons.Default.MoreVert, null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyGroupDetails(response: StudyGroupResponse, onDismissClick: () -> Unit) {
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
                headlineText = { Text("${response.studentsCount} чел.") },
                leadingContent = { Icon(Icons.Default.Group, null) },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )
            ListItem(
                headlineText = {
                    Text(
                        response.startStudyDate.toString("dd MMM YYY") + " - "
                                + response.endStudyDate.toString("dd MMM YYY")
                    )
                },
                leadingContent = { Icon(Icons.Default.DateRange, null) },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )
            ListItem(
                headlineText = { Text("Форма обучения") },
                supportingText = {
                    Text(
                        when (response.educationForm) {
                            EducationForm.FULL_TIME -> "Очно"
                            EducationForm.EXTRAMURAL -> "Заочно"
                        }
                    )
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )
        }
    }
}

@Composable
@Preview
fun StudyGroupListItemPreview() {
    StudyGroupListItem(
        StudyGroupResponse(
            UUID.randomUUID(), "ПКС-4.2", 26,
            LocalDate.now(),
            LocalDate.now().plusMonths(2),
            EducationForm.FULL_TIME
        ),
        false
    ) {}
}