package com.denchic45.kiro.preferences

import android.content.Context
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsFactory(private val context: Context) {
    actual fun create(name: String): ObservableSettings {
        return SharedPreferencesSettings.Factory(context).create(name)
    }
}