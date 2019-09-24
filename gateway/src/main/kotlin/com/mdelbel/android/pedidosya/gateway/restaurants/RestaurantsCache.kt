package com.mdelbel.android.pedidosya.gateway.restaurants

import com.mdelbel.android.pedidosya.domain.Restaurant

class RestaurantsCache internal constructor(private val cache: MutableList<Restaurant> = mutableListOf()) {

    fun obtainAll() = cache.toList()

    internal fun clearAndAddAll(restaurants: List<Restaurant>) {
        clear()
        addAll(restaurants)
    }

    internal fun addAll(restaurants: List<Restaurant>) {
        cache.addAll(restaurants)
    }

    private fun clear() = cache.clear()
}