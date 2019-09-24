package com.mdelbel.android.pedidosya.gateway.restaurants

import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.RestaurantCollection

class RestaurantsCache internal constructor
    (private val cache: MutableMap<CacheKey, RestaurantCollection> = mutableMapOf()) {

    fun obtainOn(point: Point, country: Country): RestaurantCollection {
        val key = CacheKey(point, country)
        return cache.getOrPut(key) { RestaurantCollection() }
    }

    internal data class CacheKey(val point: Point, val country: Country)
}