package com.denchic45.kiro.preferences

import com.russhwolf.settings.*


class UserPreferences(private val settings: ObservableSettings) : Settings by settings {
    var firstName: String? by nullableString()
    var surname: String? by nullableString()
    var patronymic: String? by nullableString()
    var gender: String? by nullableString()
    var avatarUrl: String? by nullableString()
    var email: String? by nullableString()
}