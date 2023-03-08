package com.denchic45.kiro

import android.app.Application
import com.denchic45.kiro.di.AndroidAppComponent
import com.denchic45.kiro.di.ApiComponent
import com.denchic45.kiro.di.PreferencesComponent
import com.denchic45.kiro.di.create
import com.denchic45.kiro.preferences.SettingsFactory
import io.ktor.client.engine.android.*

class App : Application() {
    val appComponent: AndroidAppComponent = AndroidAppComponent::class.create(
        apiComponent = ApiComponent::class.create(Android),
        preferencesComponent = PreferencesComponent::class.create(SettingsFactory(applicationContext))
    )
}