package com.kiro.feature.user

import com.kiro.api.auth.model.SignupRequest
import com.kiro.api.user.model.Account
import com.kiro.api.user.model.CreateUserRequest
import com.kiro.api.user.model.UserResponse
import com.kiro.database.table.UserDao

fun SignupRequest.toCreateUser() = CreateUserRequest(
    firstName = firstName,
    surname = surname,
    patronymic = patronymic,
    email = email
)

fun UserDao.toUserResponse() = UserResponse(
    id = id.value,
    firstName = firstName,
    surname = surname,
    patronymic = patronymic,
    account = Account(email)
)