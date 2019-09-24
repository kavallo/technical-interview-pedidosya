package com.mdelbel.android.pedidosya.presentation.restaurants.map

import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsCache

class RestaurantsOnMapViewModel(private val restaurantsCache: RestaurantsCache) : ViewModel() {

    fun obtainAll() = restaurantsCache.obtainAll()
}