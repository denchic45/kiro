package com.kiro.feature.course

import com.kiro.feature.course.usecase.*
import org.koin.dsl.module

private val useCaseModule = module {
    single { AddCourseUseCase(get(), get()) }
    single { FindCourseByIdUseCase(get(), get()) }
    single { FindCoursesUseCase(get(), get()) }
    single { UpdateCourseUseCase(get()) }
    single { RemoveCourseUseCase(get(), get()) }
}

private val repositoryModule = module { single { CourseRepository() } }

val courseModule = module {
    includes(
        useCaseModule,
        repositoryModule,
    )
}