package com.mdelbel.android.pedidosya.domain

data class Restaurant(
    val id: Long,
    val name: String,
    val coordinates: Point,
    val score: Double,
    val logoUrl: String,
    val deliveryTime: String,
    val hasOnlinePaymentMethods: Boolean
)