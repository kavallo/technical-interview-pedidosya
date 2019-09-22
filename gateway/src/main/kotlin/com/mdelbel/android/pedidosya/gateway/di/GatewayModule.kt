package com.mdelbel.android.pedidosya.gateway.di

import com.mdelbel.android.pedidosya.gateway.RestaurantsRepository
import com.mdelbel.android.pedidosya.gateway.api.ApiClient
import org.koin.dsl.module

val topMoviesGatewayModule = module {

    single { ApiClient.createVehiclesService() }

    single { RestaurantsRepository(service = get()) }
}