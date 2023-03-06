package com.denchic45.kiro.di

import com.denchic45.kiro.api.auth.AuthApi
import com.denchic45.kiro.api.auth.model.RefreshTokenRequest
import com.denchic45.kiro.preferences.AppPreferences
import com.github.michaelbull.result.unwrap
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class ApiComponent(@get:Provides protected val engine: HttpClientEngineFactory<CIOEngineConfig>) {

    @Provides
    protected fun guestClient(): GuestHttpClient = HttpClient(engine) {
        installContentNegotiation()
    }

    @Provides
    protected fun authedClient(appPreferences: AppPreferences, authApi: AuthApi): AuthHttpClient = HttpClient(engine) {
        installContentNegotiation()
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(appPreferences.token, appPreferences.refreshToken)
                }
                refreshTokens {
                    val result = authApi.refreshToken(RefreshTokenRequest(oldTokens!!.refreshToken)).unwrap()
                    BearerTokens(result.token, result.refreshToken)
                }
            }
        }
    }
}

typealias GuestHttpClient = HttpClient

typealias AuthHttpClient = HttpClient

private fun HttpClientConfig<out HttpClientEngineConfig>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}