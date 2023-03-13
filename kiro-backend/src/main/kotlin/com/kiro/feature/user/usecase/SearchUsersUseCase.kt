package com.kiro.feature.user.usecase

import com.kiro.feature.user.UserRepository
import com.kiro.transaction.TransactionWorker
import com.studiversity.util.searchable

class SearchUsersUseCase(
    private val transactionWorker: TransactionWorker,
    private val userRepository: UserRepository
) {
    operator fun invoke(query: String) = transactionWorker {
        userRepository.find(query.searchable())
    }
}