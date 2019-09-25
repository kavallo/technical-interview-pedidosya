package com.mdelbel.android.pedidosya.gateway.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.gateway.PagedListing
import com.mdelbel.android.pedidosya.gateway.RequestState

class RestaurantsRepository internal constructor(
    private val service: RestaurantsService,
    private val cache: RestaurantsCache
) {

    companion object {
        private const val PAGE_SIZE = 20
    }

    fun fetchNearTo(point: Point, country: Country): PagedListing<Restaurant> {
        val factory = RestaurantDataSourceFactory(point, country, service, cache)

        return PagedListing(
            pagedList = pageFrom(factory),
            requestState = requestStateFrom(factory)
        )
    }

    private fun pageFrom(factory: RestaurantDataSourceFactory): LiveData<PagedList<Restaurant>> {
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