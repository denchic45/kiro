package com.denchic45.kiro.di

import me.tatarka.inject.annotations.Component

abstract class AppComponent(@Component val apiComponent: ApiComponent)