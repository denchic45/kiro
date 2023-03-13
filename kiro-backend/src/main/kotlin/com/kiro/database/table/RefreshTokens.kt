package com.kiro.database.table

import com.kiro.database.type.timestampWithTimeZone
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object RefreshTokens : LongIdTable("refresh_token","refresh_token_id") {
    val token = text("token")
    val userId = reference("user_id", Users, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val expireAt = timestampWithTimeZone("expire_at")
}