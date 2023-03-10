package com.denchic45.kiro.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.denchic45.kiro.ui.courses.CoursesScreen
import com.denchic45.kiro.ui.studygroups.StudyGroupsComponent
import com.denchic45.kiro.ui.studygroups.StudyGroupsScreen
import com.denchic45.kiro.ui.theme.spacing

@Composable
fun MainScreen(component: MainComponent) {
    Surface {
        Row {
            val childStack by component.childStack.subscribeAsState()
            val active = childStack.active.instance
            NavigationRail(header = { Spacer(Modifier.height(32.dp))}) {
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
            Children(stack = component.childStack,
                modifier = Modifier.fillMaxSize().padding(MaterialTheme.spacing.small),
            animation = stackAnimation(fade() + scale())
            ) {
                when (val child = it.instance) {
                    is CoursesChild -> CoursesScreen(child.component)
                    is StudyGroupsChild -> StudyGroupsScreen(child.component)
                }
            }
        }
    }
}

