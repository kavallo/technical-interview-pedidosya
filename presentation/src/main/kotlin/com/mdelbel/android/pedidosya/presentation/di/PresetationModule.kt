package com.mdelbel.android.pedidosya.presentation.di

import com.mdelbel.android.pedidosya.presentation.AuthenticationViewModel
import com.mdelbel.android.pedidosya.presentation.RestaurantsViewModel
import com.mdelbel.android.pedidosya.presentation.location.UserLocationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { AuthenticationViewModel(authenticationGateway = get()) }

    viewModel { UserLocationViewModel(userLocationRepository = get()) }

    viewModel {
        RestaurantsViewModel(userLocationRepository = get(), restaurantsRepository = get())
    }
}