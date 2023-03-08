package com.denchic45.kiro.preferences

import com.russhwolf.settings.*
import com.russhwolf.settings.coroutines.getStringOrNullFlow

@OptIn(ExperimentalSettingsApi::class)
class AppPreferences(private val settings: ObservableSettings) : ObservableSettings by settings {
    var token by string(defaultValue = "")
    var refreshToken by string(defaultValue = "")

    val tokenFlow = getStringOrNullFlow("token")
}