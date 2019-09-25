package com.mdelbel.android.pedidosya.presentation.restaurants.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    internal val selected = MutableLiveData<Restaurant>()

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    internal val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    internal fun fetchRestaurantsFromCache() {
        val point = userLocationRepository.obtainLastKnown(default = Point.Montevideo)
        val restaurantsOnCache = restaurantsCache.obtainOn(point, Uruguay).asList()

        notifyFirstAsSelectedIfNotNull(restaurantsOnCache)
        _restaurants.postValue(restaurantsOnCache)
    }

    private fun notifyFirstAsSelectedIfNotNull(restaurants: List<Restaurant>) {
        val selectedRestaurant = restaurants.firstOrNull()
        if (selectedRestaurant != null)
            selected.postValue(selectedRestaurant)
    }
}