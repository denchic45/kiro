package com.studiversity.database.table

import com.denchic45.kiro.database.table.Users
import com.studiversity.database.type.timestampWithTimeZone
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object RefreshTokens : LongIdTable("refresh_token") {
    val token = text("token")
    val userId = reference("user_id", Users, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val expireAt = timestampWithTimeZone("expire_at")
}