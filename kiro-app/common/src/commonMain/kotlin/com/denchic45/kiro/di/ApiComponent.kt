package com.denchic45.kiro.di

import com.denchic45.kiro.preferences.AppPreferences
import com.github.michaelbull.result.expect
import com.github.michaelbull.result.unwrapError
import com.kiro.api.auth.AuthApi
import com.kiro.api.auth.model.RefreshTokenRequest
import com.kiro.api.course.CourseApi
import com.kiro.api.studygroup.StudyGroupApi
import com.kiro.api.user.UserApi
import com.kiro.util.ErrorResponse
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@LayerScope
@Component
abstract class ApiComponent(
    @get:Provides val engine: HttpClientEngineFactory<*>,
) {

    @LayerScope
    @Provides
    fun authApi(client: GuestHttpClient): AuthApi = AuthApi(client)

    @LayerScope
    @Provides
    fun guestClient():GuestHttpClient= HttpClient(engine) {
        defaultRequest {
            url("http://127.0.0.1:8080")
        }
        installContentNegotiation()
    }

    @LayerScope
    @Provides
    fun authedClient(appPreferences: AppPreferences): HttpClient = HttpClient(engine) {
        defaultRequest {
            url("http://127.0.0.1:8080")
        }
        installContentNegotiation()
        install(WebSockets)
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(appPreferences.token ?: "", appPreferences.refreshToken)
                }
                refreshTokens {
                    val result = AuthApi(client)
                        .refreshToken(RefreshTokenRequest(oldTokens!!.refreshToken))
                    val unwrapped = result.expect {
                        val unwrapError: ErrorResponse = result.unwrapError()
                        unwrapError.error.toString() + unwrapError.code
                    }
                    BearerTokens(unwrapped.token, unwrapped.refreshToken)
                }
            }
        }
    }

    @LayerScope
    @Provides
    fun userApi(client: HttpClient): UserApi = UserApi(client)

    @LayerScope
    @Provides
    fun courseApi(client: HttpClient): CourseApi = CourseApi(client)

    @LayerScope
    @Provides
    fun studyGroupApi(client: HttpClient): StudyGroupApi = StudyGroupApi(client)
}

typealias GuestHttpClient = HttpClient

private fun HttpClientConfig<out HttpClientEngineConfig>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}