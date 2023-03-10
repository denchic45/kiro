package com.kiro.feature.user


import com.kiro.api.user.model.UserResponse
import com.kiro.api.auth.model.SignupRequest
import com.kiro.api.user.model.CreateUserRequest
import com.kiro.database.table.UserDao
import com.kiro.database.table.Users
import com.kiro.database.exists
import com.kiro.database.table.RefreshTokens
import com.kiro.feature.auth.model.RefreshToken
import com.kiro.feature.auth.model.UserByEmail
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.mindrot.jbcrypt.BCrypt
import java.time.Instant
import java.util.*

class UserRepository {

    fun add(signupRequest: SignupRequest) {
        val hashed: String = BCrypt.hashpw(signupRequest.password, BCrypt.gensalt())
        add(signupRequest.toCreateUser(), hashed)
    }

    fun add(user: CreateUserRequest, password: String): UserResponse = UserDao.new {
        firstName = user.firstName
        surname = user.surname
        patronymic = user.patronymic
        email = user.email
        this.password = password
    }.toUserResponse()

    fun findById(id: UUID): UserResponse? {
        return UserDao.findById(id)?.toUserResponse()
    }

    fun remove(userId: UUID): Boolean {
        return Users.deleteWhere { Users.id eq userId } == 1
    }

//    fun update(userId: UUID, updateAccountPersonalRequest: UpdateAccountPersonalRequest) {
//        UserDao.findById(userId)!!.apply {
//            updateAccountPersonalRequest.firstName.ifPresent {
//                firstName = it
//            }
//            updateAccountPersonalRequest.surname.ifPresent {
//                surname = it
//            }
//            updateAccountPersonalRequest.patronymic.ifPresent {
//                patronymic = it
//            }
//        }
//    }

//    fun update(userId: UUID, updatePasswordRequest: UpdatePasswordRequest) {
//        UserDao.findById(userId)!!.apply {
//            if (!BCrypt.checkpw(updatePasswordRequest.oldPassword, password))
//                throw BadRequestException(AuthErrors.INVALID_PASSWORD)
//            password = BCrypt.hashpw(updatePasswordRequest.newPassword, BCrypt.gensalt())
//        }
//    }

//    fun update(userId: UUID, updateEmailRequest: UpdateEmailRequest) {
//        UserDao.findById(userId)!!.email = updateEmailRequest.email
//    }

    fun findUserByEmail(email: String): UserResponse? {
        return UserDao.find(Users.email eq email).singleOrNull()?.toUserResponse()
    }

    fun findEmailPasswordByEmail(email: String): UserByEmail? {
        return UserDao.find(Users.email eq email).singleOrNull()
            ?.let { UserByEmail(it.id.value, it.email, it.password) }
    }

    fun addToken(createRefreshToken: RefreshToken): String {
        return RefreshTokens.insert {
            it[userId] = createRefreshToken.userId
            it[token] = createRefreshToken.token
            it[expireAt] = createRefreshToken.expireAt
        }[RefreshTokens.token]
    }

    fun existByEmail(email: String): Boolean {
        return Users.exists { Users.email eq email }
    }

    fun findRefreshToken(refreshToken: String): RefreshToken? {
        val expiredTokens = RefreshTokens.select(RefreshTokens.expireAt less Instant.now()).limit(100)
            .map { it[RefreshTokens.id] }
        RefreshTokens.deleteWhere { RefreshTokens.id inList expiredTokens }
        return RefreshTokens.select(RefreshTokens.token eq refreshToken)
            .singleOrNull()?.let {
                RefreshToken(
                    it[RefreshTokens.userId].value,
                    it[RefreshTokens.token],
                    it[RefreshTokens.expireAt]
                )
            }
    }

    fun removeRefreshToken(refreshToken: String) {
        RefreshTokens.deleteWhere { token eq refreshToken }
    }

//    fun addMagicLink(magicLinkToken: MagicLinkToken): String {
//        return MagicLinks.insert {
//            it[token] = magicLinkToken.token
//            it[userId] = magicLinkToken.userId
//            it[expireAt] = magicLinkToken.expireAt
//        }[MagicLinks.token]
//    }

//    fun removeMagicLink(token: String) {
//        MagicLinks.deleteWhere { this.token eq token }
//    }

//    fun findMagicLinkByToken(token: String): MagicLinkToken? {
//        val expiredTokens = MagicLinks.select(MagicLinks.expireAt less Instant.now()).limit(100)
//            .map { it[MagicLinks.id] }
//        MagicLinks.deleteWhere { MagicLinks.id inList expiredTokens }
//
//        return MagicLinks.select(MagicLinks.token eq token).singleOrNull()?.let {
//            MagicLinkToken(
//                userId = it[MagicLinks.userId].value,
//                token = it[MagicLinks.token],
//                expireAt = it[MagicLinks.expireAt]
//            )
//        }
//    }

    fun find(query: String): List<UserResponse> = UserDao.find(
        Users.firstName.lowerCase() like "$query%"
                or (Users.surname.lowerCase() like "$query%")
                or (Users.patronymic.lowerCase() like "$query%")
    ).map(UserDao::toUserResponse)
}