package com.mdelbel.android.pedidosya.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.domain.Uruguay
import com.mdelbel.android.pedidosya.gateway.RequestState
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsRepository

class RestaurantsViewModel(repository: RestaurantsRepository) : ViewModel() {

    internal val pages: LiveData<PagedList<Restaurant>>
    internal val state: LiveData<RequestState>

    // TODO pass as parameter from geolocation
    init {
        val pagedListing = repository.fetchNearTo(Point.Montevideo, Uruguay)

        pages = pagedListing.pagedList
        state = pagedListing.requestState
    }
}