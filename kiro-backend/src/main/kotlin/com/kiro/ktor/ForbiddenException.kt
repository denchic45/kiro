package com.kiro.ktor

class ForbiddenException(message: String? = "PERMISSION_DENIED") : Exception(message)