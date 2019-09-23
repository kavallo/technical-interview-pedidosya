package com.mdelbel.android.pedidosya.gateway.restaurants.dto

import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

class RestaurantCollectionDtoTest {

    @Test
    fun `nextPage with more elements should return next page value`() {
        val restaurantsCollectionDto =
            RestaurantCollectionDto(total = 100, max = 20, count = 20, data = mock())

        val nextPage = restaurantsCollectionDto.nextPage(current = 3, pageSize = 20)

        assertThat(nextPage).isEqualTo(4)
    }

    @Test
    fun `nextPage with no more elements should return null`() {
        val restaurantsCollectionDto =
            RestaurantCollectionDto(total = 100, max = 20, count = 20, data = mock())

        val nextPage = restaurantsCollectionDto.nextPage(current = 4, pageSize = 20)

        assertThat(nextPage).isNull()
    }

    @Test
    fun `asModel should create a list of restaurant`() {
        val restaurant = mock<Restaurant>()
        val restaurantDto = mock<RestaurantDto> { on { asModel() } doReturn restaurant }
        val restaurantsCollectionDto =
            RestaurantCollectionDto(total = 1, max = 1, count = 1, data = listOf(restaurantDto))

        val restaurants = restaurantsCollectionDto.asModel()

        assertThat(restaurants).containsExactly(restaurant)
    }
}