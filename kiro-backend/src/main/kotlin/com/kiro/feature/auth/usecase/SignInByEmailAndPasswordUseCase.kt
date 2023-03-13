package com.kiro.feature.auth.usecase

import com.kiro.api.auth.AuthErrors
import com.kiro.api.auth.model.SignInByEmailPasswordRequest
import com.kiro.feature.user.UserRepository
import com.studiversity.feature.auth.model.RefreshToken
import com.kiro.transaction.TransactionWorker
import io.ktor.server.plugins.*
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class SignInByEmailAndPasswordUseCase(
    private val transactionWorker: TransactionWorker,
    private val userRepository: UserRepository
) {
    operator fun invoke(signInByEmailPasswordRequest: SignInByEmailPasswordRequest) =
        transactionWorker {
            val userByEmail = userRepository.findEmailPasswordByEmail(signInByEmailPasswordRequest.email)
                ?: throw BadRequestException(AuthErrors.INVALID_EMAIL)
            if (!BCrypt.checkpw(signInByEmailPasswordRequest.password, userByEmail.password))
                throw BadRequestException(AuthErrors.INVALID_PASSWORD)

            userByEmail.id to userRepository.addToken(
                RefreshToken(
                    userByEmail.id,
                    UUID.randomUUID().toString(),
                    LocalDateTime.now().plusWeeks(1).toInstant(ZoneOffset.UTC)
                )
            )
        }
}