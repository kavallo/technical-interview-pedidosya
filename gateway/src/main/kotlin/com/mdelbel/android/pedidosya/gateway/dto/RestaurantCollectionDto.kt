package com.mdelbel.android.pedidosya.gateway.dto

import com.mdelbel.android.pedidosya.domain.Restaurant

data class RestaurantCollectionDto(
    val total: Int,
    val max: Int,
    val count: Int,
    val data: List<RestaurantDto>
) {

    fun nextPage(current: Int, pageSize: Int): Int? {
        val itemsNotDisplayedYet = total - (count + (pageSize * current))
        val pageNotDisplayedYet = itemsNotDisplayedYet / pageSize

        return when (pageNotDisplayedYet > 0) {
            true -> current + 1
            else -> null
        }
    }

    fun asModel(): List<Restaurant> = data.map { restaurantDto -> restaurantDto.asModel() }
}