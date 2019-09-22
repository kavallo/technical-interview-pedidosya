package com.mdelbel.android.pedidosya.gateway

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.gateway.api.RestaurantsService
import com.mdelbel.android.pedidosya.gateway.dto.QueryDto
import com.mdelbel.android.pedidosya.gateway.paging.RestaurantDataSourceFactory

class RestaurantsRepository internal constructor(private val service: RestaurantsService) {

    companion object {
        private const val PAGE_SIZE = 20
    }

    fun fetchNearTo(point: Point, country: Country): PagedListing<Restaurant> {
        val query = QueryDto(point, country)
        val factory = RestaurantDataSourceFactory(query, service)

        return PagedListing(
            pagedList = pagedListFrom(factory),
            requestState = requestStateFrom(factory)
        )
    }

    private fun pagedListFrom(factory: RestaurantDataSourceFactory): LiveData<PagedList<Restaurant>> {
        val pagedListConfiguration = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

        return factory.toLiveData(pagedListConfiguration)
    }

    private fun requestStateFrom(factory: RestaurantDataSourceFactory): LiveData<RequestState> =
        Transformations.switchMap(factory.sourceLiveData) { it.requestState }
}