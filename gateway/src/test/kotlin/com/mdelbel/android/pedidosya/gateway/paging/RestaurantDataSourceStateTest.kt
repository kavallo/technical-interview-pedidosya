package com.mdelbel.android.pedidosya.gateway.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.domain.RestaurantsQuery
import com.mdelbel.android.pedidosya.gateway.Failed
import com.mdelbel.android.pedidosya.gateway.Loaded
import com.mdelbel.android.pedidosya.gateway.Loading
import com.mdelbel.android.pedidosya.gateway.RequestState
import com.mdelbel.android.pedidosya.gateway.api.RestaurantsService
import com.nhaarman.mockitokotlin2.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor

class RestaurantDataSourceStateTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `loadInitial should post loaded`() {
        val query = mock<RestaurantsQuery> {
            on { countryId() } doReturn 1
            on { pointAsString() } doReturn "-34.9033,-56.1882"
        }
        val service = mock<RestaurantsService> {
            on {
                fetchRestaurants(
                    coordinate = "-34.9033,-56.1882",
                    countryId = 1,
                    page = 1,
                    pageSize = 20
                )
            } doReturn mock()
        }
        val source = RestaurantDataSource(query, service)

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
        val query = mock<RestaurantsQuery> {
            on { countryId() } doReturn 1
            on { pointAsString() } doReturn "-34.9033,-56.1882"
        }
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
        val source = RestaurantDataSource(query, service)
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
        val query = mock<RestaurantsQuery> {
            on { countryId() } doReturn 1
            on { pointAsString() } doReturn "-34.9033,-56.1882"
        }
        val service = mock<RestaurantsService> {
            on {
                fetchRestaurants(
                    coordinate = "-34.9033,-56.1882",
                    countryId = 1,
                    page = 2,
                    pageSize = 20
                )
            } doReturn mock()
        }
        val source = RestaurantDataSource(query, service)

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
        val query = mock<RestaurantsQuery> {
            on { countryId() } doReturn 1
            on { pointAsString() } doReturn "-34.9033,-56.1882"
        }
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
        val source = RestaurantDataSource(query, service)

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