package com.denchic45.kiro.util

import java.util.*

val UUID?.orMe: String
    get() = this?.toString() ?: "me"