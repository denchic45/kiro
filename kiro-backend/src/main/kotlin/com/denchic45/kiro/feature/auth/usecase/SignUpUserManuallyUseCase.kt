package com.denchic45.kiro.feature.auth.usecase

import com.denchic45.kiro.api.user.model.CreateUserRequest
import com.denchic45.kiro.feature.auth.PasswordGenerator
import com.denchic45.kiro.feature.user.UserRepository
import com.denchic45.kiro.transaction.SuspendTransactionWorker
import com.denchic45.kiro.util.EmailSender
import org.mindrot.jbcrypt.BCrypt

class SignUpUserManuallyUseCase(
    private val transactionWorker: SuspendTransactionWorker,
    private val userRepository: UserRepository,
    private val emailSender: EmailSender
) {
    suspend operator fun invoke(createUserRequest: CreateUserRequest) = transactionWorker.suspendInvoke {
        val password = PasswordGenerator().generate()
        val user = userRepository.add(createUserRequest, BCrypt.hashpw(password, BCrypt.gensalt()))
        emailSender.sendSimpleEmail(
            createUserRequest.email,
            "Регистрация",
            generateEmailMessage(createUserRequest.firstName, createUserRequest.email, password)
        )
        user
    }

    private fun generateEmailMessage(firstName: String, email: String, password: String): String {
        return """
            Здравствуйте, $firstName
            
            Вы были успешно зарегистрированы! Ваши данные для авторизации:
            email: $email
            пароль: $password
        """.trimIndent()
    }
}