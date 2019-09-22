package com.mdelbel.android.pedidosya.gateway.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.RestaurantsQuery
import com.mdelbel.android.pedidosya.gateway.api.RestaurantsService
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RestaurantDataSourceFactoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `create should post created data source to live data`() {
        val query = mock<RestaurantsQuery>()
        val service = mock<RestaurantsService>()
        val factory = RestaurantDataSourceFactory(query, service)

        val dataSource = factory.create()

        assertThat(dataSource).isEqualTo(factory.sourceLiveData.value)
    }
}