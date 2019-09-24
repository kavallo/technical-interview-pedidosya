package com.mdelbel.android.pedidosya.presentation

import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.domain.Uruguay
import com.mdelbel.android.pedidosya.gateway.PagedListing
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsRepository

class RestaurantsViewModel(
    private val userLocationRepository: UserLocationRepository,
    private val restaurantsRepository: RestaurantsRepository
) : ViewModel() {

    internal fun fetchRestaurantsNearLastKnownLocation(): PagedListing<Restaurant> {
        val point = userLocationRepository.obtainLastKnown(default = Point.Montevideo)
        return restaurantsRepository.fetchNearTo(point, Uruguay)
    }
}