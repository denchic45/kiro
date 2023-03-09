package com.denchic45.kiro.di


import com.denchic45.kiro.config
import com.denchic45.kiro.feature.studygroup.studyGroupModule
import com.studiversity.database.DatabaseFactory
import com.studiversity.database.DatabaseFactoryImpl
import com.denchic45.kiro.feature.auth.authModule
import com.denchic45.kiro.feature.user.userModule
import com.studiversity.transaction.DatabaseTransactionWorker
import com.studiversity.transaction.SuspendTransactionWorker
import com.studiversity.transaction.TransactionWorker
import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.binds
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val otherModule = module {
    single { CoroutineScope(SupervisorJob()) }
    single<DatabaseFactory> {
        DatabaseFactoryImpl(
            config.database.url,
            config.database.driver,
            config.database.user,
            config.database.password,
        )
    }
    factory { DatabaseTransactionWorker() } binds arrayOf(TransactionWorker::class, SuspendTransactionWorker::class)
}

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(
            otherModule,
            authModule,
            userModule,
            studyGroupModule,
        )
    }
}