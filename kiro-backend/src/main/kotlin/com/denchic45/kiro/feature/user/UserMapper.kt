package com.denchic45.kiro.feature.user

import com.denchic45.kiro.api.auth.model.SignupRequest
import com.denchic45.kiro.api.user.model.Account
import com.denchic45.kiro.api.user.model.CreateUserRequest
import com.denchic45.kiro.api.user.model.UserResponse
import com.denchic45.kiro.database.table.UserDao

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
    account = Account(email),
    avatarUrl = avatarUrl,
    gender = gender
)