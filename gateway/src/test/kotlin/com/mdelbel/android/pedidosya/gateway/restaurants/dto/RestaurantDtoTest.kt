package com.mdelbel.android.pedidosya.gateway.restaurants.dto

import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import org.junit.Test

class RestaurantDtoTest {

    @Test
    fun `asModel should return Restaurant object`() {
        val expected = Restaurant(
            id = 123,
            name = "La cocina de Matias",
            description = "Milanesas,Bebidas,Picadas,Pizzas,Postres,Hamburguesas",
            coordinates = Point(latitude = -34.8895, longitude = -56.1907),
            score = 5.0,
            logoUrl = "https://img.pystatic.com/la-fachada-logo.jpg",
            deliveryTime = "Entre 60' y 90'"
        )
        val restaurantDto = RestaurantDto(
            id = 123,
            name = "La cocina de Matias",
            coordinates = "-34.8895,-56.1907",
            generalScore = 5.0,
            allCategories = "Milanesas,Bebidas,Picadas,Pizzas,Postres,Hamburguesas",
            logo = "la-fachada-logo.jpg",
            deliveryTime = "Entre 60' y 90'"
        )

        val restaurant = restaurantDto.asModel()

        assertThat(restaurant).isEqualTo(expected)
    }
}