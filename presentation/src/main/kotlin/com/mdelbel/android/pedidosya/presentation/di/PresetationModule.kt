package com.mdelbel.android.pedidosya.presentation.di

import com.mdelbel.android.pedidosya.presentation.location.UserLocationViewModel
import com.mdelbel.android.pedidosya.presentation.splash.PreConditionsMonitorViewModel
import com.mdelbel.android.pedidosya.presentation.restaurants.list.RestaurantsOnListViewModel
import com.mdelbel.android.pedidosya.presentation.restaurants.map.RestaurantsOnMapViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        PreConditionsMonitorViewModel(authenticationGateway = get(), userLocationProvider = get())
    }

    viewModel {
        UserLocationViewModel(userLocationRepository = get())
    }

    viewModel {
        RestaurantsOnListViewModel(userLocationRepository = get(), restaurantsRepository = get())
    }

    viewModel {
        RestaurantsOnMapViewModel(userLocationRepository = get(), restaurantsCache = get())
    }
}