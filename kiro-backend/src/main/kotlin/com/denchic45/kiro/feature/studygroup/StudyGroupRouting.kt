package com.denchic45.kiro.feature.studygroup

import com.denchic45.kiro.api.studygroup.model.CreateStudyGroupRequest
import com.denchic45.kiro.feature.studygroup.usecase.*
import com.denchic45.kiro.ktor.getUuid
import com.denchic45.kiro.util.toUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.studyGroupRoutes() {
    routing {
        authenticate("auth-jwt") {
            route("/studygroups") {
                studyGroupByIdRoutes()
            }

            route("/courses/{courseId}/studygroups") {
                val addStudyGroup: AddStudyGroupUseCase by inject()
                val findStudyGroupsByCourseId: FindStudyGroupsByCourseIdUseCase by inject()

                post {
                    val body = call.receive<CreateStudyGroupRequest>()
                    val courseId = call.parameters.getUuid("courseId")
                    val response = addStudyGroup(body, courseId)
                    call.respond(HttpStatusCode.Created, response)
                }
                get {
                    val courseId = call.parameters.getUuid("courseId")
                    val response = findStudyGroupsByCourseId(courseId)
                    call.respond(HttpStatusCode.OK, response)
                }
            }
        }
    }
}

private fun Route.studyGroupByIdRoutes() {
    route("/{id}") {
        val findStudyGroupById: FindStudyGroupByIdUseCase by inject()
        val updateStudyGroup: UpdateStudyGroupUseCase by inject()
        val removeStudyGroup: RemoveStudyGroupUseCase by inject()

        get {
            val id = call.parameters["id"]!!.toUUID()
            findStudyGroupById(id).let { group ->
                call.respond(HttpStatusCode.OK, group)
            }
        }
        post {
            val id = call.parameters["id"]!!.toUUID()
            val body = call.receive<CreateStudyGroupRequest>()
            updateStudyGroup(id, body)
            call.respond(HttpStatusCode.OK, "Group updated")
        }
        delete {
            val id = call.parameters["id"]!!.toUUID()
            removeStudyGroup(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}