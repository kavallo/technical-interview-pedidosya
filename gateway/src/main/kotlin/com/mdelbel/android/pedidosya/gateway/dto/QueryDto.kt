package com.mdelbel.android.pedidosya.gateway.dto

import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point

internal data class QueryDto(private val point: Point, private val country: Country) {

    fun countryId() = country.id

    fun pointAsString() = point.asString()
}