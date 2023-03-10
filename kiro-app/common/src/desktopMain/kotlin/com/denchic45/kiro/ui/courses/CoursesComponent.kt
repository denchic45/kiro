package com.denchic45.kiro.ui.courses

import com.arkivanov.decompose.ComponentContext
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class CoursesComponent(
    @Assisted
    componentContext: ComponentContext
) : ComponentContext by componentContext {

}