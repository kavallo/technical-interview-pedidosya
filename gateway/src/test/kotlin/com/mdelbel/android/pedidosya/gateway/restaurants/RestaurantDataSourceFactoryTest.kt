package com.mdelbel.android.pedidosya.gateway.restaurants

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RestaurantDataSourceFactoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `create should post created data source to live data`() {
        val point = mock<Point>()
        val country = mock<Country>()
        val service = mock<RestaurantsService>()
        val factory = RestaurantDataSourceFactory(point, country, service)

        val dataSource = factory.create()

        assertThat(dataSource).isEqualTo(factory.sourceLiveData.value)
    }
}