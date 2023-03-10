package com.denchic45.kiro.ui.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.denchic45.kiro.ui.courses.CoursesComponent
import com.denchic45.kiro.ui.studygroups.StudyGroupsComponent
import me.tatarka.inject.annotations.Inject


@Inject
class MainComponent(
    componentContext: ComponentContext,
    private val coursesComponent: (componentContext: ComponentContext) -> CoursesComponent,
    private val studyGroupsComponent: (componentContext: ComponentContext) -> StudyGroupsComponent
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<MainConfig>()

    val childStack = childStack(
        source = navigation,
        initialConfiguration = CoursesConfig,
        handleBackButton = true, // Pop the back stack on back button press
        childFactory = ::createChild,
    )

    private fun createChild(
        config: MainConfig,
        componentContext: ComponentContext
    ): MainChild = when (config) {
        is CoursesConfig -> CoursesChild(coursesComponent(componentContext))
        is StudyGroupsConfig -> StudyGroupsChild(studyGroupsComponent(componentContext))
    }

    fun onCoursesClick() {
        navigation.bringToFront(CoursesConfig)
    }

    fun onStudyGroupsClick() {
        navigation.bringToFront(StudyGroupsConfig)
    }
}

sealed class MainConfig : Parcelable

@Parcelize
object CoursesConfig : MainConfig()

@Parcelize
object StudyGroupsConfig : MainConfig()

sealed class MainChild
class CoursesChild(val component: CoursesComponent) : MainChild()
class StudyGroupsChild(val component: StudyGroupsComponent) : MainChild()