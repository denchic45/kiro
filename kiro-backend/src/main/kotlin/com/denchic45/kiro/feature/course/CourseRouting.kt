package com.denchic45.kiro.feature.course

import com.denchic45.kiro.api.course.model.CreateCourseRequest
import com.denchic45.kiro.feature.course.usecase.FindCourseByIdUseCase
import com.denchic45.kiro.feature.course.usecase.FindCoursesUseCase
import com.denchic45.kiro.feature.course.usecase.RemoveCourseUseCase
import com.denchic45.kiro.feature.course.usecase.UpdateCourseUseCase
import com.denchic45.kiro.util.toUUID
import com.denchic45.kiro.feature.course.usecase.AddCourseUseCase
import com.denchic45.kiro.ktor.claimId
import com.denchic45.kiro.ktor.getUuid
import com.studiversity.ktor.jwtPrincipal
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.courseRoutes() {
    routing {
        authenticate("auth-jwt") {
            route("/courses") {

                val addCourse: AddCourseUseCase by inject()
                val findCourses: FindCoursesUseCase by inject()

                get {
                    call.respond(HttpStatusCode.OK, findCourses())
                }

                post {
                    val currentUserId = call.principal<JWTPrincipal>()!!.payload.getClaim("sub").asString().toUUID()

                    val body = call.receive<CreateCourseRequest>()

                    val course = addCourse(body)
                    call.respond(HttpStatusCode.OK, course)
                }
                courseByIdRoute()
            }
        }
    }
}

private fun Route.courseByIdRoute() {
    route("/{courseId}") {
        val findCourseById: FindCourseByIdUseCase by inject()
        val updateCourse: UpdateCourseUseCase by inject()
        val removeCourse: RemoveCourseUseCase by inject()

        get {
            val courseId = call.parameters.getUuid("courseId")

            findCourseById(courseId).let { course -> call.respond(HttpStatusCode.OK, course) }
        }

        post {
            val courseId = call.parameters.getUuid("courseId")

            val body = call.receive<CreateCourseRequest>()

            updateCourse(courseId, body).let { course ->
                call.respond(HttpStatusCode.OK, course)
            }
        }

        delete {
            val currentUserId = call.jwtPrincipal().payload.claimId
            val courseId = call.parameters.getUuid("courseId")

            removeCourse(courseId)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}