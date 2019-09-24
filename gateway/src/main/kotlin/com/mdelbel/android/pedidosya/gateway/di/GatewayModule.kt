package com.mdelbel.android.pedidosya.gateway.di

import android.content.Context.MODE_PRIVATE
import com.mdelbel.android.pedidosya.gateway.api.ApiClient
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationGateway
import com.mdelbel.android.pedidosya.gateway.authentication.Keychain
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val gatewayModule = module {

    single { androidApplication().getSharedPreferences("LOCATION_PREFS", MODE_PRIVATE) }

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

    single { UserLocationRepository(preferences = get()) }

    single { AuthenticationGateway(service = get(), keychain = get()) }
}