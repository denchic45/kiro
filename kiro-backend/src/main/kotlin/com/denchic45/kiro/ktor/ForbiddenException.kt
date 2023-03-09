package com.denchic45.kiro.ktor

class ForbiddenException(message: String? = "PERMISSION_DENIED") : Exception(message)