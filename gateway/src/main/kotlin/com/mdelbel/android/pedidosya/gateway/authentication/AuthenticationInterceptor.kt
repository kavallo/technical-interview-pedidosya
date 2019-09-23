package com.mdelbel.android.pedidosya.gateway.authentication

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class AuthenticationInterceptor(private val keychain: Keychain) : Interceptor {

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return when (keychain.accessToken) {
            null -> chain.proceed(chain.request())
            else -> chain.proceed(authenticateRequest(chain.request(), keychain.accessToken!!))
        }
    }

    private fun authenticateRequest(request: Request, accessToken: AccessToken) =
        request
            .newBuilder()
            .header(AUTHORIZATION_HEADER_NAME, accessToken.access_token)
            .build()
}