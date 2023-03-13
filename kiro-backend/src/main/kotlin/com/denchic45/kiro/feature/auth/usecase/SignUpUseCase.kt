package com.denchic45.kiro.feature.auth.usecase

import com.denchic45.kiro.feature.user.UserRepository
import com.denchic45.kiro.api.auth.AuthErrors
import com.denchic45.kiro.api.auth.model.SignupRequest
import com.denchic45.kiro.transaction.SuspendTransactionWorker
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