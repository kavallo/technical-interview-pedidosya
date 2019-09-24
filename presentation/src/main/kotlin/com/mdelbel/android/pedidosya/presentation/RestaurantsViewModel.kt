package com.mdelbel.android.pedidosya.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.domain.Uruguay
import com.mdelbel.android.pedidosya.gateway.RequestState
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsRepository

class RestaurantsViewModel(
    userLocationRepository: UserLocationRepository,
    restaurantsRepository: RestaurantsRepository
) : ViewModel() {

    internal val pages: LiveData<PagedList<Restaurant>>
    internal val state: LiveData<RequestState>

    init {
        val point = userLocationRepository.obtainLastKnown(Point.Montevideo)
        val pagedListing = restaurantsRepository.fetchNearTo(point, Uruguay)

        pages = pagedListing.pagedList
        state = pagedListing.requestState
    }
}