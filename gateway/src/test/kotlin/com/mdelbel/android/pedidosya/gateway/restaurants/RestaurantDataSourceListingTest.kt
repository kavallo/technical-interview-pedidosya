package com.mdelbel.android.pedidosya.gateway.restaurants

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PageKeyedDataSource
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.gateway.restaurants.dto.RestaurantCollectionDto
import com.nhaarman.mockitokotlin2.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Call
import retrofit2.Response

class RestaurantDataSourceListingTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `loadInitial should call callback with fetched initial movies`() {
        val point = mock<Point> { on { asString() } doReturn "-34.9033,-56.1882" }
        val country = mock<Country> { on { id } doReturn 1 }
        val expectedRestaurants = listOf<Restaurant>(mock())
        val expectedRestaurantsDto = mock<RestaurantCollectionDto> {
            on { asModel() } doReturn expectedRestaurants
            on { nextPage(1, 20) } doReturn 2
        }
        val call = mock<Call<RestaurantCollectionDto>> {
            on { execute() } doReturn Response.success(expectedRestaurantsDto)
        }
        val service = mock<RestaurantsService> {
            on {
                fetchRestaurants(
                    coordinate = "-34.9033,-56.1882",
                    countryId = 1,
                    page = 1,
                    pageSize = 20
                )
            } doReturn call
        }
        val source = RestaurantDataSource(point, country, service)

        val params = PageKeyedDataSource.LoadInitialParams<Int>(20, true)
        val callback = mock<PageKeyedDataSource.LoadInitialCallback<Int, Restaurant>>()

        source.loadInitial(params, callback)

        argumentCaptor<List<Restaurant>> {
            verify(callback).onResult(capture(), eq(null), eq(2))
            assertThat(firstValue).containsExactlyElementsIn(expectedRestaurants)
        }
    }

    @Test
    fun `loadAfter should call callback with fetched next movies`() {
        val point = mock<Point> { on { asString() } doReturn "-34.9033,-56.1882" }
        val country = mock<Country> { on { id } doReturn 1 }
        val expectedRestaurants = listOf<Restaurant>(mock())
        val expectedRestaurantsDto = mock<RestaurantCollectionDto> {
            on { asModel() } doReturn expectedRestaurants
            on { nextPage(2, 20) } doReturn 3
        }
        val call = mock<Call<RestaurantCollectionDto>> {
            on { execute() } doReturn Response.success(expectedRestaurantsDto)
        }
        val service = mock<RestaurantsService> {
            on {
                fetchRestaurants(
                    coordinate = "-34.9033,-56.1882",
                    countryId = 1,
                    page = 2,
                    pageSize = 20
                )
            } doReturn call
        }
        val source = RestaurantDataSource(point, country, service)

        val params = PageKeyedDataSource.LoadParams(2, 20)
        val callback = mock<PageKeyedDataSource.LoadCallback<Int, Restaurant>>()

        source.loadAfter(params, callback)

        argumentCaptor<List<Restaurant>> {
            verify(callback).onResult(capture(), eq(3))
            assertThat(firstValue).containsExactlyElementsIn(expectedRestaurants)
        }
    }

    @Test
    fun `loadBefore should not fetched next movies`() {
        val point = mock<Point> { on { asString() } doReturn "-34.9033,-56.1882" }
        val country = mock<Country> { on { id } doReturn 1 }
        val service = mock<RestaurantsService>()
        val source = RestaurantDataSource(point, country, service)

        val params = mock<PageKeyedDataSource.LoadParams<Int>>()
        val callback = mock<PageKeyedDataSource.LoadCallback<Int, Restaurant>>()

        source.loadBefore(params, callback)

        verifyZeroInteractions(callback)
    }
}