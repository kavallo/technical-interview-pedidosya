package com.mdelbel.android.pedidosya.gateway.di

import com.mdelbel.android.pedidosya.gateway.api.ApiClient
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationGateway
import com.mdelbel.android.pedidosya.gateway.authentication.Keychain
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsRepository
import org.koin.dsl.module

val gatewayModule = module {

    single { Keychain() }

    single { ApiClient(keychain = get()) }

    single {
        val apiClient = get<ApiClient>()
        apiClient.createRestaurantsService()
    }

    single {
        val apiClient = get<ApiClient>()
        apiClient.createAuthenticationService()
    }

    single { RestaurantsRepository(service = get()) }

    single { AuthenticationGateway(service = get(), keychain = get()) }
}