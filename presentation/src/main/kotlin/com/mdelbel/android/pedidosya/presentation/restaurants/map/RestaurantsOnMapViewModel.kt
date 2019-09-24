package com.mdelbel.android.pedidosya.presentation.restaurants.map

import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.domain.Uruguay
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsCache

class RestaurantsOnMapViewModel(
    private val userLocationRepository: UserLocationRepository,
    private val restaurantsCache: RestaurantsCache
) : ViewModel() {

    fun obtainAll(): List<Restaurant> {
        val point = userLocationRepository.obtainLastKnown(default = Point.Montevideo)
        return restaurantsCache.obtainOn(point, Uruguay).asList()
    }
}