package com.denchic45.kiro.di

import com.denchic45.kiro.api.auth.AuthApi
import com.denchic45.kiro.api.auth.model.RefreshTokenRequest
import com.denchic45.kiro.api.course.CourseApi
import com.denchic45.kiro.api.user.UserApi
import com.denchic45.kiro.preferences.AppPreferences
import com.github.michaelbull.result.unwrap
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
@LayerScope
@Component
abstract class ApiComponent(
    @get:Provides val engine: HttpClientEngineFactory<*>,
) {

//    @Provides
//    protected fun guestClient(): GuestHttpClient = HttpClient(engine) {
//        installContentNegotiation()
//    }

    @LayerScope
    @Provides
    fun authApi(client: HttpClient): AuthApi = AuthApi(client)

    @LayerScope
    @Provides
    fun authedClient(appPreferences: AppPreferences): HttpClient = HttpClient(engine) {
        installContentNegotiation()
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(appPreferences.token, appPreferences.refreshToken)
                }
                refreshTokens {
                    val result =
                        AuthApi(client).refreshToken(RefreshTokenRequest(oldTokens!!.refreshToken))
                            .unwrap()
                    BearerTokens(result.token, result.refreshToken)
                }
            }
        }
    }

    @LayerScope
    @Provides
    fun userApi(client: HttpClient): UserApi = UserApi(client)

    @LayerScope
    @Provides
    fun courseApi(client: HttpClient):CourseApi = CourseApi(client)
}

//typealias GuestHttpClient = HttpClient

//typealias AuthHttpClient = HttpClient

private fun HttpClientConfig<out HttpClientEngineConfig>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}