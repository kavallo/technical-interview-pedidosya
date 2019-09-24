package com.mdelbel.android.pedidosya.domain

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

class RestaurantCollectionTest {

    @Test
    fun `addAll should add restaurants to collection`() {
        val restaurantCollection = RestaurantCollection()
        val restaurants = listOf<Restaurant>(mock(), mock())

        restaurantCollection.addAll(restaurants)

        assertThat(restaurantCollection.asList()).containsExactlyElementsIn(restaurants)
    }
}