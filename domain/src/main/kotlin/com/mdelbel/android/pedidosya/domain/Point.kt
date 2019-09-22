package com.mdelbel.android.pedidosya.domain

data class Point(private val latitude: Double, private val longitude: Double) {

    fun asString() = "$latitude,$longitude"
}