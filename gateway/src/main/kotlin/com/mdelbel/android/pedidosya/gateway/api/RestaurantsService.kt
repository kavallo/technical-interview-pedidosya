package com.mdelbel.android.pedidosya.gateway.api

import com.mdelbel.android.pedidosya.gateway.dto.RestaurantCollectionDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RestaurantsService {

    @GET("search/restaurants")
    fun fetchRestaurants(
        @Query("point") coordinate: String,
        @Query("country") countryId: Int,
        @Query("offset") page: Int,
        @Query("max") pageSize: Int
    ): RestaurantCollectionDto
}