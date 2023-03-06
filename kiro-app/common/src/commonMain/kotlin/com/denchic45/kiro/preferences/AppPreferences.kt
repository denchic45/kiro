package com.denchic45.kiro.preferences

import com.russhwolf.settings.*
import com.russhwolf.settings.coroutines.getIntFlow
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class AppPreferences(private val settings: Settings) : Settings by settings {
    var token by string(defaultValue =  "")
    var refreshToken by string(defaultValue = "")
}