package com.mdelbel.android.pedidosya.domain

data class RestaurantsQuery(private val point: Point, private val country: Country) {

    fun countryId() = country.id

    fun pointAsString() = point.asString()
}