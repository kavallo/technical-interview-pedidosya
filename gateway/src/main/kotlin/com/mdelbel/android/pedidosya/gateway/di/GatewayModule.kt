package com.mdelbel.android.pedidosya.gateway.di

import android.content.Context.MODE_PRIVATE
import com.google.android.gms.location.LocationServices
import com.mdelbel.android.pedidosya.gateway.api.ApiClient
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationGateway
import com.mdelbel.android.pedidosya.gateway.authentication.Keychain
import com.mdelbel.android.pedidosya.gateway.location.UserLocationProvider
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsCache
import com.mdelbel.android.pedidosya.gateway.restaurants.RestaurantsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val gatewayModule = module {

    /*---- LOCATION ----*/

    single { LocationServices.getFusedLocationProviderClient(androidApplication()) }

    single { androidApplication().getSharedPreferences("LOCATION_PREFS", MODE_PRIVATE) }

    single { UserLocationRepository(preferences = get()) }

    single { UserLocationProvider(source = get(), repository = get()) }

    /*---- API ----*/

    single { ApiClient(keychain = get()) }

    /*---- AUTHENTICATION ----*/

    single { Keychain() }

    single { get<ApiClient>().createAuthenticationService() }

    single { AuthenticationGateway(service = get(), keychain = get()) }

    /*---- RESTAURANTS ----*/

    single { get<ApiClient>().createRestaurantsService() }

    single { RestaurantsCache() }

    single { RestaurantsRepository(service = get(), cache = get()) }
}