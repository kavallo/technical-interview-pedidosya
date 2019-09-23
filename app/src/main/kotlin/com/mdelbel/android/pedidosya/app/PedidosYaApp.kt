package com.mdelbel.android.pedidosya.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.mdelbel.android.pedidosya.gateway.di.gatewayModule
import com.mdelbel.android.pedidosya.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PedidosYaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PedidosYaApp)
            modules(listOf(gatewayModule, presentationModule))
        }

        Fresco.initialize(this)
    }
}