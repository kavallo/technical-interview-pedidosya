package com.mdelbel.android.pedidosya.gateway.restaurants

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.*
import com.mdelbel.android.pedidosya.gateway.Failed
import com.mdelbel.android.pedidosya.gateway.Loaded
import com.mdelbel.android.pedidosya.gateway.Loading
import com.mdelbel.android.pedidosya.gateway.RequestState
import com.mdelbel.android.pedidosya.gateway.restaurants.dto.RestaurantCollectionDto
import com.nhaarman.mockitokotlin2.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor
import retrofit2.Call
import retrofit2.Response

class RestaurantDataSourceStateTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `loadInitial should post loaded`() {
        val point = mock<Point> { on { asString() } doReturn "-34.9033,-56.1882" }
        val call = mock<Call<RestaurantCollectionDto>> {
            on { execute() } doReturn Response.success(mock())
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
        val cache = mock<RestaurantsCache> {
            on { obtainOn(point, Uruguay) } doReturn RestaurantCollection()
        }
        val source = RestaurantDataSource(point, Uruguay, service, cache)

        val params = PageKeyedDataSource.LoadInitialParams<Int>(20, true)
        val callback = mock<PageKeyedDataSource.LoadInitialCallback<Int, Restaurant>>()

        val observer: Observer<RequestState> = mock()
        source.requestState.observeForever(observer)

        source.loadInitial(params, callback)

        with(ArgumentCaptor.forClass(RequestState::class.java)) {
            verify(observer, times(2)).onChanged(capture())
            assertThat(firstValue).isSameInstanceAs(Loading)
            assertThat(secondValue).isSameInstanceAs(Loaded)
        }
    }

    @Test
    fun `loadInitial with an error should post fail with the cause`() {
        val point = mock<Point> { on { asString() } doReturn "-34.9033,-56.1882" }
        val country = mock<Country> { on { id } doReturn 1 }
        val cause = mock<RuntimeException>()
        val service = mock<RestaurantsService> {
            on {
                fetchRestaurants(
                    coordinate = "-34.9033,-56.1882",
                    countryId = 1,
                    page = 1,
                    pageSize = 20
                )
            } doThrow cause
        }
        val cache = mock<RestaurantsCache>()
        val source = RestaurantDataSource(point, country, service, cache)
        val params = PageKeyedDataSource.LoadParams(1, 20)
        val callback = mock<PageKeyedDataSource.LoadCallback<Int, Restaurant>>()

        val observer: Observer<RequestState> = mock()
        source.requestState.observeForever(observer)

        source.loadAfter(params, callback)

        with(ArgumentCaptor.forClass(RequestState::class.java)) {
            verify(observer, times(2)).onChanged(capture())
            assertThat(firstValue).isSameInstanceAs(Loading)
            assertThat((secondValue as Failed).cause).isSameInstanceAs(cause)
        }
    }

    @Test
    fun `loadAfter should post loaded`() {
        val point = mock<Point> { on { asString() } doReturn "-34.9033,-56.1882" }
        val call = mock<Call<RestaurantCollectionDto>> {
            on { execute() } doReturn Response.success(mock())
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
        val cache = mock<RestaurantsCache> {
            on { obtainOn(point, Uruguay) } doReturn RestaurantCollection()
        }
        val source = RestaurantDataSource(point, Uruguay, service, cache)

        val params = PageKeyedDataSource.LoadParams(2, 20)
        val callback = mock<PageKeyedDataSource.LoadCallback<Int, Restaurant>>()

        val observer: Observer<RequestState> = mock()
        source.requestState.observeForever(observer)

        source.loadAfter(params, callback)

        with(ArgumentCaptor.forClass(RequestState::class.java)) {
            verify(observer, times(2)).onChanged(capture())
            assertThat(firstValue).isSameInstanceAs(Loading)
            assertThat(secondValue).isSameInstanceAs(Loaded)
        }
    }

    @Test
    fun `loadAfter with an error should post fail with the cause`() {
        val point = mock<Point> { on { asString() } doReturn "-34.9033,-56.1882" }
        val country = mock<Country> { on { id } doReturn 1 }
        val cause = mock<RuntimeException>()
        val service = mock<RestaurantsService> {
            on {
                fetchRestaurants(
                    coordinate = "-34.9033,-56.1882",
                    countryId = 1,
                    page = 2,
                    pageSize = 20
                )
            } doThrow cause
        }
        val cache = mock<RestaurantsCache>()
        val source = RestaurantDataSource(point, country, service, cache)

        val params = PageKeyedDataSource.LoadParams(2, 20)
        val callback = mock<PageKeyedDataSource.LoadCallback<Int, Restaurant>>()

        val observer: Observer<RequestState> = mock()
        source.requestState.observeForever(observer)

        source.loadAfter(params, callback)

        with(ArgumentCaptor.forClass(RequestState::class.java)) {
            verify(observer, times(2)).onChanged(capture())
            assertThat(firstValue).isSameInstanceAs(Loading)
            assertThat((secondValue as Failed).cause).isSameInstanceAs(cause)
        }
    }
}