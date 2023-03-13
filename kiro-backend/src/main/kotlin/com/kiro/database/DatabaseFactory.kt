package com.kiro.database

import org.jetbrains.exposed.sql.Database

interface DatabaseFactory {
    val database: Database
}

class DatabaseFactoryImpl(
    url: String,
    user: String,
    password: String
) : DatabaseFactory {

    override val database by lazy {
        Database.connect(
            url = url,
            user = user,
            password = password
        )
    }
}