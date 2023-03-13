package com.kiro.feature.course

import com.kiro.api.course.model.CreateCourseRequest
import com.kiro.feature.course.usecase.*
import com.kiro.ktor.getUuid
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
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
            val courseId = call.parameters.getUuid("courseId")
            removeCourse(courseId)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}