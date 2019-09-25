package com.mdelbel.android.pedidosya.domain

data class RestaurantCollection
    (private val restaurants: MutableList<Restaurant> = mutableListOf()) {

    fun addAll(restaurantsToAdd: List<Restaurant>) = restaurants.addAll(restaurantsToAdd)

    fun asList() = restaurants.toList()
}