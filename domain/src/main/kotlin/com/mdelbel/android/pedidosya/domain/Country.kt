package com.mdelbel.android.pedidosya.domain

sealed class Country(val id: Int)

object Uruguay : Country(id = 1)