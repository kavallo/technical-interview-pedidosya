package com.mdelbel.android.pedidosya.gateway.restaurants.dto

import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant

data class RestaurantDto(
    val id: Long,
    val name: String,
    val coordinates: String,
    val generalScore: Double,
    val logo: String,
    val deliveryTime: String,
    val hasOnlinePaymentMethods: Boolean
) {

    fun asModel(): Restaurant {
        val coordinatesValues = coordinates.split(",")
        val coordinatesPoint = Point(
            latitude = coordinatesValues[0].toDouble(),
            longitude = coordinatesValues[1].toDouble()
        )

        return Restaurant(
            id = id,
            name = name,
            coordinates = coordinatesPoint,
            score = generalScore,
            logoUrl = "https://img.pystatic.com/restaurants/$logo",
            deliveryTime = deliveryTime,
            hasOnlinePaymentMethods = hasOnlinePaymentMethods
        )
    }
}