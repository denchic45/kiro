package com.studiversity.feature.user.usecase

import com.denchic45.kiro.feature.user.UserRepository
import com.denchic45.kiro.transaction.TransactionWorker
import com.studiversity.util.searchable

class SearchUsersUseCase(
    private val transactionWorker: TransactionWorker,
    private val userRepository: UserRepository
) {
    operator fun invoke(query: String) = transactionWorker {
        userRepository.find(query.searchable())
    }
}