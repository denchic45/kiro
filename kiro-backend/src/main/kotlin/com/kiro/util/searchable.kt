package com.kiro.util

fun String.searchable() = trim().lowercase().replace("\\s+","%")