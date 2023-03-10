package com.denchic45.kiro.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.denchic45.kiro.ui.theme.DesktopTheme

@Composable
fun MainScreen(component: MainComponent) {
        Surface {
            Row {
                val childStack by component.childStack.subscribeAsState()
                val active = childStack.active.instance
                NavigationRail {
                    NavigationRailItem(
                        selected = active is CoursesChild,
                        onClick = { component.onCoursesClick() },
                        icon = { Icon(Icons.Outlined.School, null) },
                        label = { Text("Курсы") }
                    )
                    NavigationRailItem(
                        selected = active is StudyGroupsChild,
                        onClick = { component.onStudyGroupsClick() },
                        icon = { Icon(Icons.Outlined.Groups, null) },
                        label = { Text("Группы") }
                    )
                }
                Box { }
            }
        }
    }