package com.denchic45.kiro.preferences

import com.russhwolf.settings.ObservableSettings

expect class SettingsFactory {
    fun create(name: String): ObservableSettings
}