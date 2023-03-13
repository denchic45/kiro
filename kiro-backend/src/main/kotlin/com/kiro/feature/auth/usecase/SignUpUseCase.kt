package com.kiro.feature.auth.usecase

import com.kiro.feature.user.UserRepository
import com.kiro.api.auth.AuthErrors
import com.kiro.api.auth.model.SignupRequest
import com.kiro.transaction.SuspendTransactionWorker
import io.ktor.server.plugins.*

class SignUpUseCase(
    private val transactionWorker: SuspendTransactionWorker,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(signupRequest: SignupRequest) = transactionWorker.suspendInvoke {
        if (userRepository.existByEmail(signupRequest.email)) throw BadRequestException(AuthErrors.USER_ALREADY_REGISTERED)
        userRepository.add(signupRequest)
    }
}