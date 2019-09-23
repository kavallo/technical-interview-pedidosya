package com.mdelbel.android.pedidosya.gateway.api

import com.google.gson.GsonBuilder
import com.mdelbel.android.pedidosya.gateway.BuildConfig
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationInterceptor
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationService
import com.mdelbel.android.pedidosya.gateway.authentication.Keychain
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class ApiClient(keychain: Keychain) {

    private val gson = GsonBuilder().create()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthenticationInterceptor(keychain))
        .addInterceptor(HttpLoggingInterceptor().apply { level = BODY })
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.PEDIDOS_YA_API)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    internal fun createRestaurantsService(): RestaurantsService =
        retrofit.create(RestaurantsService::class.java)

    internal fun createAuthenticationService(): AuthenticationService =
        retrofit.create(AuthenticationService::class.java)
}