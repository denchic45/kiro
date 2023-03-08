package com.denchic45.kiro.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings

actual class SettingsFactory {
    actual fun create(name: String): ObservableSettings {
        return PreferencesSettings.Factory().create(name)
    }
}