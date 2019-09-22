package com.mdelbel.android.pedidosya.gateway.api

import com.google.gson.GsonBuilder
import com.mdelbel.android.pedidosya.gateway.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object ApiClient {

    private val gson = GsonBuilder().create()

    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.PEDIDOS_YA_API)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun createVehiclesService(): RestaurantsService = retrofit.create(RestaurantsService::class.java)
}