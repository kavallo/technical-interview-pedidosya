package com.mdelbel.android.pedidosya.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.domain.Uruguay
import com.mdelbel.android.pedidosya.gateway.PagedListing
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsRepository
import com.mdelbel.android.pedidosya.presentation.restaurants.list.RestaurantsViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test

class RestaurantsViewModelTest {

    @get:Rule
    var testRule = InstantTaskExecutorRule()

    @Test
    fun `fetchRestaurantsNearLastKnownLocation should obtain restaurants near by`() {
        val point = mock<Point>()
        val expected = mock<PagedListing<Restaurant>>()
        val userLocationRepository = mock<UserLocationRepository> {
            on { obtainLastKnown(any()) } doReturn point
        }
        val restaurantsRepository = mock<RestaurantsRepository> {
            on { fetchNearTo(point, Uruguay) } doReturn expected
        }
        val viewModel =
            RestaurantsViewModel(
                userLocationRepository,
                restaurantsRepository
            )

        val result = viewModel.fetchRestaurantsNearLastKnownLocation()

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `fetchRestaurantsNearLastKnownLocation should pass Montevideo as default`() {
        val userLocationRepository = mock<UserLocationRepository>()
        val restaurantsRepository = mock<RestaurantsRepository>()
        val viewModel =
            RestaurantsViewModel(
                userLocationRepository,
                restaurantsRepository
            )

        viewModel.fetchRestaurantsNearLastKnownLocation()

        verify(userLocationRepository).obtainLastKnown(Point.Montevideo)
    }
}