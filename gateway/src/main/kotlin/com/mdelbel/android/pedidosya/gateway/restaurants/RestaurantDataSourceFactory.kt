package com.mdelbel.android.pedidosya.gateway.restaurants

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant

internal class RestaurantDataSourceFactory(
    private val point: Point,
    private val country: Country,
    private val service: RestaurantsService
) : DataSource.Factory<Int, Restaurant>() {

    internal val sourceLiveData = MutableLiveData<RestaurantDataSource>()

    override fun create(): DataSource<Int, Restaurant> {
        val dataSource = RestaurantDataSource(point, country, service)
        sourceLiveData.postValue(dataSource)

        return dataSource
    }
}