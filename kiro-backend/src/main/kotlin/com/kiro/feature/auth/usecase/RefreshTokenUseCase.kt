package com.kiro.feature.auth.usecase

import com.kiro.api.auth.AuthErrors
import com.kiro.api.auth.model.RefreshTokenRequest
import com.kiro.feature.auth.model.RefreshToken
import com.kiro.feature.user.UserRepository
import com.kiro.transaction.TransactionWorker
import io.ktor.server.plugins.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class RefreshTokenUseCase(
    private val transactionWorker: TransactionWorker,
    private val userRepository: UserRepository
) {
    operator fun invoke(refreshTokenRequest: RefreshTokenRequest) = transactionWorker {
        val foundRefreshToken = userRepository.findRefreshToken(refreshTokenRequest.refreshToken)
            ?: throw BadRequestException(AuthErrors.INVALID_REFRESH_TOKEN)

        userRepository.removeRefreshToken(refreshTokenRequest.refreshToken)

        if (foundRefreshToken.isExpired)
            throw BadRequestException(AuthErrors.INVALID_REFRESH_TOKEN)

        val userId = foundRefreshToken.userId
        val generatedRefreshToken = RefreshToken(
            userId,
            UUID.randomUUID().toString(),
            LocalDateTime.now().plusWeeks(1).toInstant(ZoneOffset.UTC)
        )
        userId to userRepository.addToken(generatedRefreshToken)
    }
}