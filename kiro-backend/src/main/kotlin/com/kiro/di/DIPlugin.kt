package com.kiro.di


import com.kiro.config
import com.kiro.feature.studygroup.studyGroupModule
import com.kiro.database.DatabaseFactory
import com.kiro.database.DatabaseFactoryImpl
import com.kiro.feature.auth.authModule
import com.kiro.feature.course.courseModule
import com.kiro.feature.user.userModule
import com.kiro.transaction.DatabaseTransactionWorker
import com.kiro.transaction.SuspendTransactionWorker
import com.kiro.transaction.TransactionWorker
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
            courseModule,
            studyGroupModule,
        )
    }
}