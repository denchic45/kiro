package com.kiro.database.table

import com.kiro.api.user.model.Gender
import com.studiversity.database.exists
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*


object Users : UUIDTable("user", "user_id") {
    val firstName = text("first_name")
    val surname = text("surname")
    val patronymic = text("patronymic").nullable()
    val email = text("email")
    val password = text("password")
    val avatarUrl = text("avatar_url")
    val gender = enumerationByName<Gender>("gender", 10)
}

class UserDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDao>(Users) {
        fun isExistEmail(email: String): Boolean {
            return table.exists { Users.email eq email }
        }
    }

    var firstName by Users.firstName
    var surname by Users.surname
    var patronymic by Users.patronymic
    var email by Users.email
    var password by Users.password
    var avatarUrl by Users.avatarUrl
    var gender by Users.gender
}