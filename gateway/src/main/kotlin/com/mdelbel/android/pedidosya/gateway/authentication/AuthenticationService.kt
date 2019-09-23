package com.mdelbel.android.pedidosya.gateway.authentication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface AuthenticationService {

    @GET("tokens")
    fun obtainAccessToken(
        @Query("clientId") clientId: String,
        @Query("clientSecret") clientSecret: String
    ): Call<AccessToken>
}