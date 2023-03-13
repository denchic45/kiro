package com.kiro.util

import java.util.*

val UUID?.orMe: String
    get() = this?.toString() ?: "me"