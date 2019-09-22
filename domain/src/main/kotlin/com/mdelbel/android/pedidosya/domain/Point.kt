package com.mdelbel.android.pedidosya.domain

data class Point(private val latitude: Double, private val longitude: Double) {

    companion object {
        val Montevideo = Point(latitude = -34.9033, longitude = -56.1882)
    }

    fun asString() = "$latitude,$longitude"
}