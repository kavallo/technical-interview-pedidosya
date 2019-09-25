package com.mdelbel.android.pedidosya.gateway.restaurants

import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.RestaurantCollection
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

class RestaurantsCacheTest {

    @Test
    fun `obtainOn with value stored should return it`() {
        val point = mock<Point>()
        val country = mock<Country>()
        val expected = mock<RestaurantCollection>()
        val cacheValues = mutableMapOf(RestaurantsCache.CacheKey(point, country) to expected)
        val cache = RestaurantsCache(cacheValues)

        val result = cache.obtainOn(point, country)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `obtainOn with value not stored should return empty value`() {
        val cache = RestaurantsCache()

        val result = cache.obtainOn(mock(), mock())

        assertThat(result.asList()).isEmpty()
    }
}