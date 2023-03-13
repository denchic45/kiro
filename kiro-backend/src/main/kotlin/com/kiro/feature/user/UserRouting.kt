package com.kiro.feature.user

import com.kiro.feature.user.usecase.FindUserByIdUseCase
import com.kiro.feature.auth.usecase.SignUpUserManuallyUseCase
import com.studiversity.feature.user.usecase.RemoveUserUseCase
import com.studiversity.feature.user.usecase.SearchUsersUseCase
import com.studiversity.ktor.currentUserId
import com.kiro.ktor.getUuid
import com.kiro.util.tryToUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject

fun Application.userRoutes() {
    routing {
        authenticate("auth-jwt") {
            route("/users") {
                val signUpUserManually: SignUpUserManuallyUseCase by inject()
                val searchUsers: SearchUsersUseCase by inject()

                get {
                    val q: String = call.request.queryParameters.getOrFail("q")
                    call.respond(HttpStatusCode.OK, searchUsers(q))
                }

                post {
                    call.respond(HttpStatusCode.Created, signUpUserManually(call.receive()))
                }
                userByIdRoute()
            }
        }
    }
}

private fun Route.userByIdRoute() {
    route("/{userId}") {
        val findUserById: FindUserByIdUseCase by inject()
        val removeUser: RemoveUserUseCase by inject()

        get {
            val currentUserId = call.currentUserId()
            val userId = when (val parameter = call.parameters.getOrFail("userId")) {
                "me" -> currentUserId
                else -> parameter.tryToUUID()
            }
            val user = findUserById(userId)
            call.respond(HttpStatusCode.OK, user)
        }

        delete {
            removeUser(call.parameters.getUuid("userId"))
            call.respond(HttpStatusCode.NoContent)
        }
    }
}


