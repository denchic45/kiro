package com.denchic45.kiro.ui.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.denchic45.kiro.ui.courseEditor.CourseEditorDialog
import com.denchic45.kiro.ui.courses.CoursesScreen
import com.denchic45.kiro.ui.studygroups.StudyGroupsScreen
import com.denchic45.kiro.ui.theme.spacing

@Composable
fun MainScreen(component: MainComponent) {
    Surface(tonalElevation = 1.dp) {

        Row {
            val childStack by component.childStack.subscribeAsState()
            val active = childStack.active.instance
            NavigationRail(header = {
                FloatingActionButton(
                    onClick = { component.onAddClick() },
                    modifier = Modifier.padding(vertical = 32.dp)
                ) {
                    Icon(Icons.Default.Add, null)
                }
            }) {
                NavigationRailItem(
                    selected = active is CoursesChild,
                    onClick = { component.onCoursesClick() },
                    icon = {
                        Icon(
                            if (active !is CoursesChild) Icons.Outlined.School
                            else Icons.Filled.School, null
                        )
                    },
                    label = { Text("Kурcы") }
                )
                NavigationRailItem(
                    selected = active is StudyGroupsChild,
                    onClick = { component.onStudyGroupsClick() },
                    icon = {
                        Icon(
                            if (active !is StudyGroupsChild) Icons.Outlined.Groups
                            else Icons.Filled.Groups, null
                        )
                    },
                    label = { Text("Группы") }
                )
            }
            Children(
                stack = component.childStack,
                modifier = Modifier.fillMaxSize().padding(MaterialTheme.spacing.normal),
                animation = stackAnimation(fade() + scale())
            ) {
                when (val child = it.instance) {
                    is CoursesChild -> CoursesScreen(child.component)
                    is StudyGroupsChild -> StudyGroupsScreen(child.component)
                }
            }

            val showCourseEditor by component.showCourseEditorDialog.collectAsState()
            if (showCourseEditor)
                CourseEditorDialog(component.courseEditorComponent)
        }
    }
}

