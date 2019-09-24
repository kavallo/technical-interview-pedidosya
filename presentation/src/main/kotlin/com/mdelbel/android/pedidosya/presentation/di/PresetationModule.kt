package com.mdelbel.android.pedidosya.presentation.di

import com.mdelbel.android.pedidosya.presentation.AuthenticationViewModel
import com.mdelbel.android.pedidosya.presentation.location.UserLocationViewModel
import com.mdelbel.android.pedidosya.presentation.restaurants.list.RestaurantsViewModel
import com.mdelbel.android.pedidosya.presentation.restaurants.map.RestaurantsOnMapViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { AuthenticationViewModel(authenticationGateway = get()) }

    viewModel { UserLocationViewModel(userLocationRepository = get()) }

    viewModel {
        RestaurantsViewModel(userLocationRepository = get(), restaurantsRepository = get())
    }

    viewModel { RestaurantsOnMapViewModel(userLocationRepository = get(), restaurantsCache = get()) }
}