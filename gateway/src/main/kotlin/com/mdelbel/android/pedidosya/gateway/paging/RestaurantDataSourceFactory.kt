package com.mdelbel.android.pedidosya.gateway.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.domain.RestaurantsQuery
import com.mdelbel.android.pedidosya.gateway.api.RestaurantsService

internal class RestaurantDataSourceFactory(
    private val query: RestaurantsQuery,
    private val service: RestaurantsService
) : DataSource.Factory<Int, Restaurant>() {

    internal val sourceLiveData = MutableLiveData<RestaurantDataSource>()

    override fun create(): DataSource<Int, Restaurant> {
        val dataSource = RestaurantDataSource(query, service)
        sourceLiveData.postValue(dataSource)

        return dataSource
    }
}