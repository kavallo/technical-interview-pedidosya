package com.mdelbel.android.pedidosya.presentation.restaurants.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.domain.RestaurantCollection
import com.mdelbel.android.pedidosya.domain.Uruguay
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsCache
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test

class RestaurantsOnMapViewModelTest {

    @get:Rule
    var testRule = InstantTaskExecutorRule()

    @Test
    fun `fetchRestaurantsFromCache should post restaurants`() {
        val point = mock<Point>()
        val restaurants = listOf<Restaurant>(mock())
        val restaurantsCollection = mock<RestaurantCollection> {
            on { asList() } doReturn restaurants
        }
        val userLocationRepository = mock<UserLocationRepository> {
            on { obtainLastKnown(default = Point.Montevideo) } doReturn point
        }
        val restaurantsCache = mock<RestaurantsCache> {
            on { obtainOn(point, Uruguay) } doReturn restaurantsCollection
        }
        val viewModel = RestaurantsOnMapViewModel(userLocationRepository, restaurantsCache)

        viewModel.fetchRestaurantsFromCache()

        assertThat(viewModel.restaurants.value).containsExactlyElementsIn(restaurants)
    }

    @Test
    fun `fetchRestaurantsFromCache with empty restaurants list should not post selected`() {
        val point = mock<Point>()
        val restaurantsCollection = mock<RestaurantCollection> {
            on { asList() } doReturn emptyList()
        }
        val userLocationRepository = mock<UserLocationRepository> {
            on { obtainLastKnown(default = Point.Montevideo) } doReturn point
        }
        val restaurantsCache = mock<RestaurantsCache> {
            on { obtainOn(point, Uruguay) } doReturn restaurantsCollection
        }
        val viewModel = RestaurantsOnMapViewModel(userLocationRepository, restaurantsCache)

        viewModel.fetchRestaurantsFromCache()

        assertThat(viewModel.selected.value).isNull()
    }

    @Test
    fun `fetchRestaurantsFromCache with restaurants list should not post first as selected`() {
        val point = mock<Point>()
        val firstRestaurant = mock<Restaurant>()
        val restaurants = listOf(firstRestaurant, mock())
        val restaurantsCollection = mock<RestaurantCollection> {
            on { asList() } doReturn restaurants
        }
        val userLocationRepository = mock<UserLocationRepository> {
            on { obtainLastKnown(default = Point.Montevideo) } doReturn point
        }
        val restaurantsCache = mock<RestaurantsCache> {
            on { obtainOn(point, Uruguay) } doReturn restaurantsCollection
        }
        val viewModel = RestaurantsOnMapViewModel(userLocationRepository, restaurantsCache)

        viewModel.fetchRestaurantsFromCache()

        assertThat(viewModel.selected.value).isEqualTo(firstRestaurant)
    }
}