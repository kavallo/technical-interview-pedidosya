package com.mdelbel.android.pedidosya.gateway.dto

import org.junit.Test

class RestaurantDtoTest {

    @Test(expected = NotImplementedError::class)
    fun `asModel should throw exception`() {
        val restaurantDto = RestaurantDto()

        restaurantDto.asModel()
    }
}