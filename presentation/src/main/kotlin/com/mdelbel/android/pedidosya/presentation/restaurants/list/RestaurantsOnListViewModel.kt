package com.mdelbel.android.pedidosya.presentation.restaurants.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.domain.Uruguay
import com.mdelbel.android.pedidosya.gateway.PagedListing
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsRepository
import com.mdelbel.android.pedidosya.presentation.restaurants.list.PermissionDelegate.PermissionEvent

class RestaurantsOnListViewModel(
    private val userLocationRepository: UserLocationRepository,
    private val restaurantsRepository: RestaurantsRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    internal val viewState: LiveData<ViewState> get() = _viewState

    internal fun fetchRestaurantsNearLastKnownLocation(): PagedListing<Restaurant> {
        val point = userLocationRepository.obtainLastKnown(default = Point.Montevideo)
        return restaurantsRepository.fetchNearTo(point, Uruguay)
    }

    internal fun onPermissionEvent(event: PermissionEvent) {
        if (event is PermissionEvent.PermissionGranted) _viewState.postValue(PermissionGrantedState)
    }
}